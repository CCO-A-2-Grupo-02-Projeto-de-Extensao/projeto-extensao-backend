package clube_tamoios.service;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.entity.Evento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
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
class EventoServiceTest {

    @Mock
    private EventoRepository repository;

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
}