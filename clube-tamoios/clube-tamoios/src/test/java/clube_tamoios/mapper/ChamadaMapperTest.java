package clube_tamoios.mapper;

import clube_tamoios.dto.response.ChamadaResponse;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Evento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChamadaMapperTest {

    @Test
    @DisplayName("Deve converter Chamada (Entity) para ChamadaResponse (DTO)")
    void deveConverterEntityParaDto() {
        Evento evento = new Evento();
        evento.setIdEvento(10);
        evento.setNome("Reunião Regular");

        Chamada chamada = new Chamada();
        chamada.setIdChamada(1);
        chamada.setEvento(evento);
        chamada.setTitulo("Chamada Matinal");
        chamada.setDataChamada(LocalDate.of(2026, 5, 20));

        ChamadaResponse dto = ChamadaMapper.toResponse(chamada);

        assertEquals(1, dto.getIdChamada());
        assertEquals(10, dto.getIdEvento());
        assertEquals("Reunião Regular", dto.getNomeEvento());
        assertEquals("Chamada Matinal", dto.getTitulo());
        assertEquals(LocalDate.of(2026, 5, 20), dto.getDataChamada());
    }

    @Test
    @DisplayName("Deve converter lista de Chamadas para lista de DTOs")
    void deveConverterListaEntityParaListaDto() {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        Chamada chamada = new Chamada();
        chamada.setIdChamada(5);
        chamada.setEvento(evento);

        List<ChamadaResponse> listaDto = ChamadaMapper.toResponse(List.of(chamada));

        assertEquals(1, listaDto.size());
        assertEquals(5, listaDto.get(0).getIdChamada());
    }
}