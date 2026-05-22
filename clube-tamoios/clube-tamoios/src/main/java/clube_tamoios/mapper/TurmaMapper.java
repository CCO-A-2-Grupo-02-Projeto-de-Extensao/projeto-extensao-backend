package clube_tamoios.mapper;

import clube_tamoios.dto.response.TurmaResponse;
import clube_tamoios.entity.Turma;

import java.util.List;

public class TurmaMapper {

    public static TurmaResponse toResponse(Turma entity) {
        TurmaResponse dto = new TurmaResponse();
        dto.setId(entity.getId());

        if (entity.getClasse() != null) {
            dto.setIdClasse(entity.getClasse().getIdClasse());
            dto.setNomeClasse(entity.getClasse().getNome());
        }

        if (entity.getUnidade() != null) {
            dto.setIdUnidade(entity.getUnidade().getIdUnidade());
            dto.setNomeUnidade(entity.getUnidade().getNome());
        }

        return dto;
    }

    public static List<TurmaResponse> toResponse(List<Turma> entities) {
        return entities.stream().map(TurmaMapper::toResponse).toList();
    }
}