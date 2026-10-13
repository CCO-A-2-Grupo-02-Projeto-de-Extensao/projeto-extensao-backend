package clube_tamoios.mapper;

import clube_tamoios.dto.response.HistoricoEscolarResponse;
import clube_tamoios.entity.HistoricoEscolar;
import java.util.List;

public class HistoricoEscolarMapper {

    public static HistoricoEscolarResponse toResponse(HistoricoEscolar entity) {
        HistoricoEscolarResponse dto = new HistoricoEscolarResponse();
        dto.setId(entity.getId());
        dto.setIdPessoa(entity.getPessoa().getIdPessoa());
        dto.setAno(entity.getAno());
        dto.setSerie(entity.getSerie());
        dto.setObediencia(entity.isObediencia());
        dto.setPrestatividade(entity.isPrestatividade());
        dto.setParticipacao(entity.isParticipacao());
        if (entity.getBoletim() != null) {
            dto.setIdBoletim(entity.getBoletim().getId());
            dto.setNomeBoletim(entity.getBoletim().getNomeOriginal());
        }
        if (entity.getDeclaracao() != null) {
            dto.setIdDeclaracao(entity.getDeclaracao().getId());
            dto.setNomeDeclaracao(entity.getDeclaracao().getNomeOriginal());
        }
        dto.setOutroClube(entity.isOutroClube());
        dto.setClubeOrigem(entity.getClubeOrigem());
        dto.setClasseConcluidaOrigem(entity.isClasseConcluidaOrigem());
        return dto;
    }

    public static List<HistoricoEscolarResponse> toResponse(List<HistoricoEscolar> entities) {
        return entities.stream().map(HistoricoEscolarMapper::toResponse).toList();
    }
}
