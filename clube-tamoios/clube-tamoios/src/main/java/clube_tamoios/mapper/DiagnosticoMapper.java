package clube_tamoios.mapper;

import clube_tamoios.dto.response.DiagnosticoResponse;
import clube_tamoios.entity.Diagnostico;
import java.util.List;

public class DiagnosticoMapper {

    public static DiagnosticoResponse toResponse(Diagnostico entity) {
        DiagnosticoResponse dto = new DiagnosticoResponse();
        dto.setId(entity.getId());
        dto.setIdFichaMedica(entity.getFichaMedica().getId());
        dto.setIdComorbidade(entity.getComorbidade().getId());
        dto.setNomeComorbidade(entity.getComorbidade().getNome());
        if (entity.getDocumento() != null) {
            dto.setIdDocumento(entity.getDocumento().getId());
            dto.setNomeDocumento(entity.getDocumento().getNomeOriginal());
        }
        return dto;
    }

    public static List<DiagnosticoResponse> toResponse(List<Diagnostico> entities) {
        return entities.stream().map(DiagnosticoMapper::toResponse).toList();
    }
}
