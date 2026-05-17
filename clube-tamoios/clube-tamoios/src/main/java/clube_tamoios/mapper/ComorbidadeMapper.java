package clube_tamoios.mapper;

import clube_tamoios.dto.response.ComorbidadeResponse;
import clube_tamoios.entity.Comorbidade;
import java.util.List;

public class ComorbidadeMapper {

    public static ComorbidadeResponse toResponse(Comorbidade entity) {
        ComorbidadeResponse dto = new ComorbidadeResponse();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        return dto;
    }

    public static List<ComorbidadeResponse> toResponse(List<Comorbidade> entities) {
        return entities.stream().map(ComorbidadeMapper::toResponse).toList();
    }
}
