package clube_tamoios.mapper;

import clube_tamoios.dto.response.OcorrenciaResponse;
import clube_tamoios.entity.Ocorrencia;
import java.util.List;

public class OcorrenciaMapper {

    public static OcorrenciaResponse toResponse(Ocorrencia entity) {
        OcorrenciaResponse dto = new OcorrenciaResponse();
        dto.setId(entity.getId());
        dto.setData(entity.getData());
        dto.setDescricao(entity.getDescricao());
        dto.setIdPessoa(entity.getPessoa().getIdPessoa());
        dto.setNomePessoa(entity.getPessoa().getNome());
        return dto;
    }

    public static List<OcorrenciaResponse> toResponse(List<Ocorrencia> entities) {
        return entities.stream().map(OcorrenciaMapper::toResponse).toList();
    }
}
