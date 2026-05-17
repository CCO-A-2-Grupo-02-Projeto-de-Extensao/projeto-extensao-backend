package clube_tamoios.mapper;

import clube_tamoios.dto.response.FichaMedicaResponse;
import clube_tamoios.entity.FichaMedica;

public class FichaMedicaMapper {

    public static FichaMedicaResponse toResponse(FichaMedica entity) {
        FichaMedicaResponse dto = new FichaMedicaResponse();
        dto.setId(entity.getId());
        dto.setIdPessoa(entity.getPessoa().getIdPessoa());
        dto.setNomePessoa(entity.getPessoa().getNome());
        dto.setMedicacoes(MedicacaoMapper.toResponse(entity.getMedicacoes()));
        dto.setDiagnosticos(DiagnosticoMapper.toResponse(entity.getDiagnosticos()));
        return dto;
    }
}
