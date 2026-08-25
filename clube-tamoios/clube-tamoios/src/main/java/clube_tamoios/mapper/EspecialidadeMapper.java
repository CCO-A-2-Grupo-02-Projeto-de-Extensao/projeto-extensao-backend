package clube_tamoios.mapper;

import clube_tamoios.dto.response.EspecialidadeResponse;
import clube_tamoios.entity.Especialidade;

import java.util.List;

public class EspecialidadeMapper {

    public static EspecialidadeResponse toResponse(Especialidade entity) {
        EspecialidadeResponse dto = new EspecialidadeResponse();
        dto.setId(entity.getIdEspecialidade());
        dto.setNome(entity.getNome());
        dto.setCategoria(entity.getCategoria());
        dto.setDescricao(entity.getDescricao());
        dto.setImagem(entity.getImagem());
        return dto;
    }

    public static List<EspecialidadeResponse> toResponse(List<Especialidade> entities) {
        return entities.stream().map(EspecialidadeMapper::toResponse).toList();
    }
}
