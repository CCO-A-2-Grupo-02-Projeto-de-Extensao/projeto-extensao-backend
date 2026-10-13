package clube_tamoios.mapper;

import clube_tamoios.dto.response.EspecialidadeConcluidaResponse;
import clube_tamoios.entity.EspecialidadeConcluida;
import java.util.List;

public class EspecialidadeConcluidaMapper {

    public static EspecialidadeConcluidaResponse toResponse(EspecialidadeConcluida entity) {
        EspecialidadeConcluidaResponse dto = new EspecialidadeConcluidaResponse();
        dto.setId(entity.getId());
        dto.setIdPessoa(entity.getPessoa().getIdPessoa());
        dto.setIdEspecialidade(entity.getEspecialidade().getIdEspecialidade());
        dto.setNomeEspecialidade(entity.getEspecialidade().getNome());
        dto.setAno(entity.getAno());
        return dto;
    }

    public static List<EspecialidadeConcluidaResponse> toResponse(List<EspecialidadeConcluida> entities) {
        return entities.stream().map(EspecialidadeConcluidaMapper::toResponse).toList();
    }
}
