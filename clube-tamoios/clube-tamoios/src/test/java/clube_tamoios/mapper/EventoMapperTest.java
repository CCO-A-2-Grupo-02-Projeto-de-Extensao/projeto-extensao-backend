package clube_tamoios.mapper;

import clube_tamoios.dto.response.EventoResponse;
import clube_tamoios.entity.Evento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoMapperTest {

    @Test
    @DisplayName("Deve converter Evento (Entity) para EventoResponse (DTO)")
    void deveConverterEntityParaDto() {
        // Cenário
        Evento evento = new Evento();
        evento.setIdEvento(1);
        evento.setNome("Acampamento de Verão");
        evento.setTipo("Acampamento");
        evento.setDataInicio(LocalDate.of(2026, 1, 10));
        evento.setDescricao("Acampamento anual");

        // Ação
        EventoResponse dto = EventoMapper.toResponse(evento);

        // Verificação
        assertEquals(1, dto.getIdEvento());
        assertEquals("Acampamento de Verão", dto.getNome());
        assertEquals("Acampamento", dto.getTipo());
        assertEquals(LocalDate.of(2026, 1, 10), dto.getDataInicio());
        assertEquals("Acampamento anual", dto.getDescricao());
    }

    @Test
    @DisplayName("Deve converter lista de Eventos para lista de EventoResponse")
    void deveConverterListaEntityParaListaDto() {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        List<EventoResponse> listaDto = EventoMapper.toResponse(List.of(evento));

        assertEquals(1, listaDto.size());
        assertEquals(1, listaDto.get(0).getIdEvento());
    }
}