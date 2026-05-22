package clube_tamoios.service;

import clube_tamoios.dto.request.ChamadaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChamadaService {
    private final ChamadaRepository chamadaRepository;
    private final EventoRepository eventoRepository;

    public ChamadaService(ChamadaRepository chamadaRepository, EventoRepository eventoRepository) {
        this.chamadaRepository = chamadaRepository;
        this.eventoRepository = eventoRepository;
    }

    public Chamada cadastrar(ChamadaRequest request) {
        Evento evento = eventoRepository.findById(request.getIdEvento())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado: " + request.getIdEvento()));

        Chamada chamada = new Chamada();
        chamada.setEvento(evento);
        chamada.setDataChamada(request.getDataChamada());
        chamada.setTitulo(request.getTitulo());
        return chamadaRepository.save(chamada);
    }

    public List<Chamada> listarPorEvento(Integer idEvento) {
        return chamadaRepository.findByEventoIdEvento(idEvento);
    }

    public Chamada buscarPorId(Integer id) {
        return chamadaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Chamada não encontrada: " + id));
    }

    public void deletar(Integer id) {
        Chamada chamada = buscarPorId(id);
        chamadaRepository.delete(chamada);
    }
}