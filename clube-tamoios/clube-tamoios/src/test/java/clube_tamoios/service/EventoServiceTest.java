package clube_tamoios.service;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository repository;

    @Mock
    private ChamadaRepository chamadaRepository;

    @InjectMocks
    private EventoService service;

    @Test
    @DisplayName("Deve cadastrar um evento com sucesso")
    void deveCadastrarEventoComSucesso() {
        EventoRequest request = new EventoRequest();
        request.setNome("Acampamento Tamoios");
        request.setTipo("Acampamento");
        request.setDataInicio(LocalDate.now());

        Evento eventoSalvo = new Evento();
        eventoSalvo.setIdEvento(1);
        eventoSalvo.setNome("Acampamento Tamoios");

        when(repository.save(any(Evento.class))).thenReturn(eventoSalvo);

        Evento resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvento());
        assertEquals("Acampamento Tamoios", resultado.getNome());
        verify(repository, times(1)).save(any(Evento.class));
    }

    @Test
    @DisplayName("Deve buscar evento por ID com sucesso")
    void deveBuscarEventoPorIdComSucesso() {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        when(repository.findById(1)).thenReturn(Optional.of(evento));

        Evento resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvento());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar evento com ID inexistente")
    void deveLancarExcecaoAoBuscarEventoInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId(99));
    }

    @Test
    @DisplayName("Deve deletar evento sem chamada")
    void deveDeletarEventoSemChamada() {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        when(repository.findById(1)).thenReturn(Optional.of(evento));
        when(chamadaRepository.findByEventoIdEvento(1)).thenReturn(List.of());

        service.deletar(1);

        verify(repository, times(1)).delete(evento);
    }

    @Test
    @DisplayName("Não deve deletar evento com chamada registrada")
    void naoDeveDeletarEventoComChamada() {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        when(repository.findById(1)).thenReturn(Optional.of(evento));
        when(chamadaRepository.findByEventoIdEvento(1)).thenReturn(List.of(new Chamada()));

        assertThrows(RegraDeNegocioException.class, () -> service.deletar(1));
        verify(repository, never()).delete(any(Evento.class));
    }

    private EventoRequest requestRepetido(String repeticao, LocalDate inicio, LocalDate fim, LocalDate ate) {
        EventoRequest request = new EventoRequest();
        request.setNome("Reunião");
        request.setDataInicio(inicio);
        request.setDataFim(fim);
        request.setRepeticao(repeticao);
        request.setRepetirAte(ate);
        return request;
    }

    private Evento ocorrencia(int id, String serie, LocalDate inicio) {
        Evento evento = new Evento();
        evento.setIdEvento(id);
        evento.setNome("Reunião");
        evento.setSerie(serie);
        evento.setRepeticao(serie == null ? null : "SEMANAL");
        evento.setDataInicio(inicio);
        evento.setDataFim(inicio);
        return evento;
    }

    private List<Evento> salvos(int quantidade) {
        ArgumentCaptor<Evento> captor = ArgumentCaptor.forClass(Evento.class);
        verify(repository, times(quantidade)).save(captor.capture());
        return captor.getAllValues();
    }

    private static final LocalDate D1 = LocalDate.of(2026, 11, 1);

    private List<Evento> serieDeTres() {
        List<Evento> serie = List.of(
                ocorrencia(1, "abc", D1),
                ocorrencia(2, "abc", D1.plusWeeks(1)),
                ocorrencia(3, "abc", D1.plusWeeks(2)));
        serie.forEach(e -> lenient().when(repository.findById(e.getIdEvento())).thenReturn(Optional.of(e)));
        lenient().when(repository.findBySerie("abc")).thenReturn(serie);
        lenient().when(chamadaRepository.findByEventoIdEvento(any())).thenReturn(List.of());
        return serie;
    }

    @Test
    @DisplayName("Deve criar uma ocorrência por semana até a data final, todas com a regra e a mesma série")
    void deveCriarOcorrenciasSemanais() {
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        Evento primeiro = service.cadastrar(requestRepetido("SEMANAL", D1, D1.plusDays(1), D1.plusWeeks(3)));

        List<Evento> eventos = salvos(4);
        assertEquals(D1, primeiro.getDataInicio());
        assertEquals(D1.plusWeeks(3).plusDays(1), eventos.get(3).getDataFim());
        assertTrue(eventos.stream().allMatch(e -> e.getSerie().equals(eventos.get(0).getSerie())));
        assertTrue(eventos.stream().allMatch(e -> "SEMANAL".equals(e.getRepeticao())));
        assertTrue(eventos.stream().allMatch(e -> D1.plusWeeks(3).equals(e.getRepetirAte())));
    }

    @Test
    @DisplayName("Deve criar ocorrências quinzenais")
    void deveCriarOcorrenciasQuinzenais() {
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        service.cadastrar(requestRepetido("QUINZENAL", D1, null, LocalDate.of(2026, 11, 28)));

        List<Evento> eventos = salvos(2);
        assertEquals(LocalDate.of(2026, 11, 15), eventos.get(1).getDataInicio());
        assertNull(eventos.get(1).getDataFim());
    }

    @Test
    @DisplayName("Repetição mensal não deve escorregar o dia depois de um mês curto")
    void deveCriarOcorrenciasMensaisSemEscorregar() {
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        service.cadastrar(requestRepetido("MENSAL", LocalDate.of(2027, 1, 31), null, LocalDate.of(2027, 3, 31)));

        List<Evento> eventos = salvos(3);
        assertEquals(LocalDate.of(2027, 2, 28), eventos.get(1).getDataInicio());
        assertEquals(LocalDate.of(2027, 3, 31), eventos.get(2).getDataInicio());
    }

    @Test
    @DisplayName("Sem data final, deve gerar um ano de ocorrências")
    void deveGerarUmAnoQuandoRepeteSemFim() {
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        service.cadastrar(requestRepetido("SEMANAL", D1, null, null));

        List<Evento> eventos = salvos(53);
        assertEquals(LocalDate.of(2027, 10, 31), eventos.get(52).getDataInicio());
        assertNull(eventos.get(0).getRepetirAte());
    }

    @Test
    @DisplayName("Não deve repetir com data final antes do início, por mais de um ano ou com repetição inválida")
    void naoDeveRepetirComDataFinalInvalida() {
        assertThrows(RegraDeNegocioException.class,
                () -> service.cadastrar(requestRepetido("SEMANAL", D1, null, D1.minusDays(1))));
        assertThrows(RegraDeNegocioException.class,
                () -> service.cadastrar(requestRepetido("SEMANAL", D1, null, D1.plusYears(1).plusDays(1))));
        assertThrows(RegraDeNegocioException.class,
                () -> service.cadastrar(requestRepetido("DIARIA", D1, null, D1.plusDays(7))));
        verify(repository, never()).save(any(Evento.class));
    }

    @Test
    @DisplayName("Deve estender as séries sem fim até um ano à frente, a partir da última ocorrência")
    void deveEstenderSeriesSemFim() {
        LocalDate primeiraData = LocalDate.now().minusWeeks(10);
        Evento primeira = ocorrencia(1, "abc", primeiraData);
        Evento ultima = ocorrencia(2, "abc", primeiraData.plusWeeks(50));
        ultima.setDataFim(primeiraData.plusWeeks(50).plusDays(1));

        when(repository.findByRepeticaoIsNotNullAndRepetirAteIsNull()).thenReturn(List.of(ultima, primeira));
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        service.estenderSeriesSemFim();

        List<Evento> novas = salvos(12);
        assertEquals(primeiraData.plusWeeks(51), novas.get(0).getDataInicio());
        assertEquals(primeiraData.plusWeeks(51).plusDays(1), novas.get(0).getDataFim());
        assertEquals("abc", novas.get(0).getSerie());
        assertFalse(novas.get(11).getDataInicio().isAfter(LocalDate.now().plusYears(1)));
    }

    @Test
    @DisplayName("Excluir 'este' numa série apaga só a ocorrência")
    void deveExcluirSoUmaOcorrencia() {
        List<Evento> serie = serieDeTres();

        service.deletar(2, EventoService.ESTE);

        verify(repository).delete(serie.get(1));
        verify(repository, never()).deleteAll(any());
    }

    @Test
    @DisplayName("Excluir 'este e os seguintes' apaga do dia em diante e encerra a série no dia anterior")
    void deveExcluirEsteEOsSeguintes() {
        List<Evento> serie = serieDeTres();

        service.deletar(2, EventoService.SEGUINTES);

        verify(repository).deleteAll(List.of(serie.get(1), serie.get(2)));
        assertEquals(D1.plusWeeks(1).minusDays(1), serie.get(0).getRepetirAte());
    }

    @Test
    @DisplayName("Excluir 'todos' mantém as ocorrências com chamada, agora fora da série")
    void deveExcluirTodosMantendoChamadas() {
        List<Evento> serie = serieDeTres();
        when(chamadaRepository.findByEventoIdEvento(1)).thenReturn(List.of(new Chamada()));

        service.deletar(3, EventoService.TODOS);

        verify(repository).deleteAll(List.of(serie.get(1), serie.get(2)));
        assertNull(serie.get(0).getSerie());
        assertNull(serie.get(0).getRepeticao());
    }

    @Test
    @DisplayName("Editar 'este' muda só a ocorrência, inclusive a data")
    void deveEditarSoUmaOcorrencia() {
        List<Evento> serie = serieDeTres();
        when(repository.save(any(Evento.class))).then(returnsFirstArg());
        EventoRequest request = requestRepetido("SEMANAL", D1.plusWeeks(1).plusDays(1), null, null);
        request.setNome("Reunião especial");

        service.atualizar(2, request, EventoService.ESTE);

        assertEquals("Reunião especial", serie.get(1).getNome());
        assertEquals(D1.plusWeeks(1).plusDays(1), serie.get(1).getDataInicio());
        assertEquals("Reunião", serie.get(0).getNome());
        assertEquals("Reunião", serie.get(2).getNome());
    }

    @Test
    @DisplayName("Editar 'este e os seguintes' sem mudar a regra aplica nome e deslocamento de data do dia em diante")
    void deveEditarEsteEOsSeguintesSemMudarRegra() {
        List<Evento> serie = serieDeTres();
        EventoRequest request = requestRepetido("SEMANAL", D1.plusWeeks(1).plusDays(1), D1.plusWeeks(1).plusDays(2), null);
        request.setNome("Reunião nova");
        request.setDescricao("  Trazer corda  ");

        service.atualizar(2, request, EventoService.SEGUINTES);

        assertEquals("Reunião", serie.get(0).getNome());
        assertEquals(D1, serie.get(0).getDataInicio());
        assertEquals("Reunião nova", serie.get(2).getNome());
        assertEquals("Trazer corda", serie.get(2).getDescricao());
        assertEquals(D1.plusWeeks(2).plusDays(1), serie.get(2).getDataInicio());
        assertEquals(D1.plusWeeks(2).plusDays(2), serie.get(2).getDataFim());
        verify(repository).saveAll(List.of(serie.get(1), serie.get(2)));
    }

    @Test
    @DisplayName("Mudar a regra em 'todos' recria a série e reaproveita a ocorrência com chamada")
    void deveRecriarSerieAoMudarRegra() {
        List<Evento> serie = serieDeTres();
        when(chamadaRepository.findByEventoIdEvento(1)).thenReturn(List.of(new Chamada()));
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        Evento resultado = service.atualizar(2, requestRepetido("QUINZENAL", D1.plusWeeks(1), null, D1.plusWeeks(4)), EventoService.TODOS);

        verify(repository).deleteAll(List.of(serie.get(1), serie.get(2)));
        List<Evento> salvas = salvos(3);
        assertSame(serie.get(0), salvas.get(0));
        assertEquals(List.of(D1, D1.plusWeeks(2), D1.plusWeeks(4)), salvas.stream().map(Evento::getDataInicio).toList());
        assertEquals("QUINZENAL", serie.get(0).getRepeticao());
        assertNotEquals("abc", serie.get(0).getSerie());
        assertEquals(D1, resultado.getDataInicio());
    }

    @Test
    @DisplayName("Mudar a regra em 'este e os seguintes' encerra a parte anterior e cria uma série nova")
    void deveDividirSerieAoMudarRegraDoDiaEmDiante() {
        List<Evento> serie = serieDeTres();
        when(repository.save(any(Evento.class))).then(returnsFirstArg());

        service.atualizar(2, requestRepetido("MENSAL", D1.plusWeeks(1), null, D1.plusWeeks(1).plusMonths(1)), EventoService.SEGUINTES);

        verify(repository).deleteAll(List.of(serie.get(1), serie.get(2)));
        assertEquals(D1.plusWeeks(1).minusDays(1), serie.get(0).getRepetirAte());
        List<Evento> novas = salvos(2);
        assertEquals(D1.plusWeeks(1).plusMonths(1), novas.get(1).getDataInicio());
        assertEquals("MENSAL", novas.get(0).getRepeticao());
    }

    @Test
    @DisplayName("Escopo inválido é recusado")
    void deveRecusarEscopoInvalido() {
        serieDeTres();

        assertThrows(RegraDeNegocioException.class, () -> service.deletar(1, "ALGUNS"));
    }
}
