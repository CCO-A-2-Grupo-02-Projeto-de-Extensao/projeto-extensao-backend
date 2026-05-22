package clube_tamoios.mapper;

import clube_tamoios.dto.response.ChamadaResponse;
import clube_tamoios.entity.Chamada;
import java.util.List;

public class ChamadaMapper {
    public static ChamadaResponse toResponse(Chamada entity) {
        ChamadaResponse dto = new ChamadaResponse();
        dto.setIdChamada(entity.getIdChamada());
        dto.setIdEvento(entity.getEvento().getIdEvento());
        dto.setNomeEvento(entity.getEvento().getNome());
        dto.setDataChamada(entity.getDataChamada());
        dto.setTitulo(entity.getTitulo());
        return dto;
    }

    public static List<ChamadaResponse> toResponse(List<Chamada> entities) {
        return entities.stream().map(ChamadaMapper::toResponse).toList();
    }
}