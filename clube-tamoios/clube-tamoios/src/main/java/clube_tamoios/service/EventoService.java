package clube_tamoios.service;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.EventoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventoService {
    public static final String ESTE = "ESTE";
    public static final String SEGUINTES = "SEGUINTES";
    public static final String TODOS = "TODOS";

    private final EventoRepository repository;
    private final ChamadaRepository chamadaRepository;

    public EventoService(EventoRepository repository, ChamadaRepository chamadaRepository) {
        this.repository = repository;
        this.chamadaRepository = chamadaRepository;
    }

    @Transactional
    public Evento cadastrar(EventoRequest request) {
        return criarOcorrencias(request, request.getDataInicio(), List.of()).get(0);
    }

    @Transactional
    public Evento atualizar(Integer id, EventoRequest request, String escopo) {
        Evento evento = buscarPorId(id);
        if (evento.getSerie() == null) escopo = TODOS;
        validarEscopo(escopo);

        if (ESTE.equals(escopo)) {
            preencher(evento, request, request.getDataInicio(), duracao(request));
            return repository.save(evento);
        }

        List<Evento> alvos = alvos(evento, escopo);
        long deslocamento = ChronoUnit.DAYS.between(evento.getDataInicio(), request.getDataInicio());
        boolean regraMudou = !Objects.equals(semBranco(request.getRepeticao()), evento.getRepeticao())
                || !Objects.equals(request.getRepetirAte(), evento.getRepetirAte());

        if (!regraMudou) {
            Long duracao = duracao(request);
            alvos.forEach(alvo -> preencher(alvo, request, alvo.getDataInicio().plusDays(deslocamento), duracao));
            repository.saveAll(alvos);
            return evento;
        }

        LocalDate inicio = alvos.get(0).getDataInicio().plusDays(deslocamento);
        List<Evento> comChamada = alvos.stream().filter(this::temChamada).toList();
        repository.deleteAll(alvos.stream().filter(alvo -> !comChamada.contains(alvo)).toList());
        encerrarAntesDe(evento, escopo);

        List<Evento> criados = criarOcorrencias(request, inicio, comChamada);
        comChamada.stream().filter(antigo -> !criados.contains(antigo)).forEach(this::desvincular);

        return criados.stream()
                .filter(criado -> criado.getDataInicio().equals(request.getDataInicio()))
                .findFirst()
                .orElse(criados.get(0));
    }

    private List<Evento> criarOcorrencias(EventoRequest request, LocalDate inicio, List<Evento> aproveitar) {
        String repeticao = semBranco(request.getRepeticao());
        List<LocalDate> datas = datasDasOcorrencias(repeticao, inicio, request.getRepetirAte());
        String serie = repeticao != null ? UUID.randomUUID().toString() : null;
        Long duracao = duracao(request);

        List<Evento> criados = new ArrayList<>();
        for (LocalDate data : datas) {
            Evento evento = aproveitar.stream()
                    .filter(antigo -> data.equals(antigo.getDataInicio()))
                    .findFirst()
                    .orElseGet(Evento::new);
            preencher(evento, request, data, duracao);
            evento.setSerie(serie);
            evento.setRepeticao(repeticao);
            evento.setRepetirAte(repeticao != null ? request.getRepetirAte() : null);
            criados.add(repository.save(evento));
        }
        return criados;
    }

    private void preencher(Evento evento, EventoRequest request, LocalDate inicio, Long duracao) {
        evento.setNome(request.getNome());
        evento.setTipo(request.getTipo());
        evento.setDataInicio(inicio);
        evento.setDataFim(duracao == null || inicio == null ? request.getDataFim() : inicio.plusDays(duracao));
        evento.setDescricao(semBranco(request.getDescricao()));
    }

    private Long duracao(EventoRequest request) {
        if (request.getDataInicio() == null || request.getDataFim() == null) return null;
        return ChronoUnit.DAYS.between(request.getDataInicio(), request.getDataFim());
    }

    private List<LocalDate> datasDasOcorrencias(String repeticao, LocalDate inicio, LocalDate ate) {
        if (repeticao == null) {
            return Collections.singletonList(inicio);
        }
        if (inicio == null) {
            throw new RegraDeNegocioException("Informe o início do evento.");
        }
        if (ate == null) {
            return datasDaSerie(repeticao, inicio, inicio.plusYears(1));
        }
        if (ate.isBefore(inicio)) {
            throw new RegraDeNegocioException("A repetição não pode terminar antes do início do evento.");
        }
        if (ate.isAfter(inicio.plusYears(1))) {
            throw new RegraDeNegocioException("A repetição pode durar no máximo um ano. Deixe sem data final para repetir sem fim.");
        }
        return datasDaSerie(repeticao, inicio, ate);
    }

    private List<LocalDate> datasDaSerie(String repeticao, LocalDate inicio, LocalDate ate) {
        List<LocalDate> datas = new ArrayList<>();
        for (int i = 0; ; i++) {
            LocalDate data = switch (repeticao) {
                case "SEMANAL" -> inicio.plusWeeks(i);
                case "QUINZENAL" -> inicio.plusWeeks(2L * i);
                case "MENSAL" -> inicio.plusMonths(i);
                default -> throw new RegraDeNegocioException("Repetição inválida: " + repeticao);
            };
            if (data.isAfter(ate)) return datas;
            datas.add(data);
        }
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void estenderSeriesSemFim() {
        LocalDate limite = LocalDate.now().plusYears(1);
        repository.findByRepeticaoIsNotNullAndRepetirAteIsNull().stream()
                .collect(Collectors.groupingBy(Evento::getSerie))
                .values()
                .forEach(ocorrencias -> {
                    Evento primeira = ocorrencias.stream().min(Comparator.comparing(Evento::getDataInicio)).orElseThrow();
                    Evento ultima = ocorrencias.stream().max(Comparator.comparing(Evento::getDataInicio)).orElseThrow();
                    datasDaSerie(primeira.getRepeticao(), primeira.getDataInicio(), limite).stream()
                            .filter(data -> data.isAfter(ultima.getDataInicio()))
                            .forEach(data -> repository.save(copiaEm(ultima, data)));
                });
    }

    private Evento copiaEm(Evento modelo, LocalDate inicio) {
        Evento evento = new Evento();
        evento.setNome(modelo.getNome());
        evento.setTipo(modelo.getTipo());
        evento.setDataInicio(inicio);
        evento.setDataFim(modelo.getDataFim() == null
                ? null
                : inicio.plusDays(ChronoUnit.DAYS.between(modelo.getDataInicio(), modelo.getDataFim())));
        evento.setDescricao(modelo.getDescricao());
        evento.setSerie(modelo.getSerie());
        evento.setRepeticao(modelo.getRepeticao());
        return evento;
    }

    public List<Evento> listar() { return repository.findAll(); }

    public Evento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado: " + id));
    }

    public void deletar(Integer id) {
        Evento evento = buscarPorId(id);
        if (temChamada(evento)) {
            throw new RegraDeNegocioException("Este evento tem uma chamada registrada. Apague a chamada na tela de Chamada antes de excluir o evento.");
        }
        repository.delete(evento);
    }

    @Transactional
    public void deletar(Integer id, String escopo) {
        validarEscopo(escopo);
        Evento evento = buscarPorId(id);
        if (evento.getSerie() == null || ESTE.equals(escopo)) {
            deletar(id);
            return;
        }

        List<Evento> alvos = alvos(evento, escopo);
        encerrarAntesDe(evento, escopo);
        List<Evento> comChamada = alvos.stream().filter(this::temChamada).toList();
        comChamada.forEach(this::desvincular);
        repository.deleteAll(alvos.stream().filter(alvo -> !comChamada.contains(alvo)).toList());
    }

    private List<Evento> alvos(Evento evento, String escopo) {
        List<Evento> serie = evento.getSerie() == null ? List.of(evento) : repository.findBySerie(evento.getSerie());
        return serie.stream()
                .filter(ocorrencia -> TODOS.equals(escopo) || !ocorrencia.getDataInicio().isBefore(evento.getDataInicio()))
                .sorted(Comparator.comparing(Evento::getDataInicio))
                .toList();
    }

    private void encerrarAntesDe(Evento evento, String escopo) {
        if (!SEGUINTES.equals(escopo) || evento.getSerie() == null) return;
        List<Evento> anteriores = repository.findBySerie(evento.getSerie()).stream()
                .filter(ocorrencia -> ocorrencia.getDataInicio().isBefore(evento.getDataInicio()))
                .toList();
        anteriores.forEach(anterior -> anterior.setRepetirAte(evento.getDataInicio().minusDays(1)));
        repository.saveAll(anteriores);
    }

    private void desvincular(Evento evento) {
        evento.setSerie(null);
        evento.setRepeticao(null);
        evento.setRepetirAte(null);
        repository.save(evento);
    }

    private boolean temChamada(Evento evento) {
        return !chamadaRepository.findByEventoIdEvento(evento.getIdEvento()).isEmpty();
    }

    private void validarEscopo(String escopo) {
        if (!List.of(ESTE, SEGUINTES, TODOS).contains(escopo)) {
            throw new RegraDeNegocioException("Escopo inválido: " + escopo);
        }
    }

    private String semBranco(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
