package clube_tamoios.mapper;

import clube_tamoios.dto.response.PresencaResponse;
import clube_tamoios.entity.Presenca;
import java.util.List;

public class PresencaMapper {

    public static PresencaResponse toResponse(Presenca entity) {
        PresencaResponse dto = new PresencaResponse();
        dto.setId(entity.getId());
        dto.setIdChamada(entity.getChamada().getIdChamada());
        dto.setIdPessoa(entity.getPessoa().getIdPessoa());
        dto.setNomePessoa(entity.getPessoa().getNome());
        dto.setPresente(entity.getPresente());
        return dto;
    }

    public static List<PresencaResponse> toResponse(List<Presenca> entities) {
        return entities.stream().map(PresencaMapper::toResponse).toList();
    }
}