package clube_tamoios.service;

import clube_tamoios.dto.request.ChamadaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadaServiceTest {

    @Mock
    private ChamadaRepository chamadaRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private ChamadaService service;

    @Test
    @DisplayName("Deve cadastrar chamada com sucesso")
    void deveCadastrarChamadaComSucesso() {
        ChamadaRequest request = new ChamadaRequest();
        request.setIdEvento(1);
        request.setTitulo("Chamada Matinal");
        request.setDataChamada(LocalDate.now());

        Evento evento = new Evento();
        evento.setIdEvento(1);

        Chamada chamadaSalva = new Chamada();
        chamadaSalva.setIdChamada(1);
        chamadaSalva.setEvento(evento);
        chamadaSalva.setTitulo("Chamada Matinal");

        when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));
        when(chamadaRepository.save(any(Chamada.class))).thenReturn(chamadaSalva);

        Chamada resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdChamada());
        assertEquals(1, resultado.getEvento().getIdEvento());
        verify(chamadaRepository, times(1)).save(any(Chamada.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar chamada para evento inexistente")
    void deveLancarExcecaoCadastroChamadaEventoInexistente() {
        ChamadaRequest request = new ChamadaRequest();
        request.setIdEvento(99);

        when(eventoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.cadastrar(request));
        verify(chamadaRepository, never()).save(any());
    }
}