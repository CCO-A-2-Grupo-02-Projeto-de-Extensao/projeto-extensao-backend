package clube_tamoios.mapper;

import clube_tamoios.dto.response.MedicamentoResponse;
import clube_tamoios.entity.Medicamento;
import java.util.List;

public class MedicamentoMapper {

    public static MedicamentoResponse toResponse(Medicamento entity) {
        MedicamentoResponse dto = new MedicamentoResponse();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        return dto;
    }

    public static List<MedicamentoResponse> toResponse(List<Medicamento> entities) {
        return entities.stream().map(MedicamentoMapper::toResponse).toList();
    }
}
