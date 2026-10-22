package clube_tamoios.service;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventoService {
    private final EventoRepository repository;
    private final ChamadaRepository chamadaRepository;

    public EventoService(EventoRepository repository, ChamadaRepository chamadaRepository) {
        this.repository = repository;
        this.chamadaRepository = chamadaRepository;
    }

    public Evento cadastrar(EventoRequest request) {
        Evento evento = new Evento();
        evento.setNome(request.getNome());
        evento.setTipo(request.getTipo());
        evento.setDataInicio(request.getDataInicio());
        evento.setDataFim(request.getDataFim());
        evento.setDescricao(request.getDescricao());
        return repository.save(evento);
    }

    public List<Evento> listar() { return repository.findAll(); }

    public Evento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado: " + id));
    }

    public void deletar(Integer id) {
        Evento evento = buscarPorId(id);
        if (!chamadaRepository.findByEventoIdEvento(id).isEmpty()) {
            throw new RegraDeNegocioException("Este evento tem uma chamada registrada. Apague a chamada na tela de Chamada antes de excluir o evento.");
        }
        repository.delete(evento);
    }
}