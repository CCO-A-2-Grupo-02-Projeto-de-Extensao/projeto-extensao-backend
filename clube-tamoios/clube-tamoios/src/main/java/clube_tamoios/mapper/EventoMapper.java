package clube_tamoios.mapper;

import clube_tamoios.dto.response.EventoResponse;
import clube_tamoios.entity.Evento;
import java.util.List;

public class EventoMapper {
    public static EventoResponse toResponse(Evento entity) {
        EventoResponse dto = new EventoResponse();
        dto.setIdEvento(entity.getIdEvento());
        dto.setNome(entity.getNome());
        dto.setTipo(entity.getTipo());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataFim(entity.getDataFim());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }

    public static List<EventoResponse> toResponse(List<Evento> entities) {
        return entities.stream().map(EventoMapper::toResponse).toList();
    }
}