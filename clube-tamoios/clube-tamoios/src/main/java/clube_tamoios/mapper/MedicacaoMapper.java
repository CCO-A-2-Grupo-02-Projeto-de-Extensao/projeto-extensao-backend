package clube_tamoios.mapper;

import clube_tamoios.dto.response.MedicacaoResponse;
import clube_tamoios.entity.Medicacao;
import java.util.List;

public class MedicacaoMapper {

    public static MedicacaoResponse toResponse(Medicacao entity) {
        MedicacaoResponse dto = new MedicacaoResponse();
        dto.setId(entity.getId());
        dto.setIdFichaMedica(entity.getFichaMedica().getId());
        dto.setIdMedicamento(entity.getMedicamento().getId());
        dto.setNomeMedicamento(entity.getMedicamento().getNome());
        dto.setHorarioInicio(entity.getHorarioInicio());
        dto.setHorarioFim(entity.getHorarioFim());
        dto.setDose(entity.getDose());
        if (entity.getDocumento() != null) {
            dto.setIdDocumento(entity.getDocumento().getId());
            dto.setNomeDocumento(entity.getDocumento().getNomeOriginal());
        }
        return dto;
    }

    public static List<MedicacaoResponse> toResponse(List<Medicacao> entities) {
        return entities.stream().map(MedicacaoMapper::toResponse).toList();
    }
}
