package clube_tamoios.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DocumentoAtualizadoMessage(
        Integer idPessoa,
        String nomePessoa,
        Integer idDocumento,
        String tipoDocumento,
        String nomeArquivo,
        LocalDateTime dataAlteracao,
        String listaDiretoria,
        String tipoAviso,
        String mensagem,
        String canal,
        boolean requerAtencaoResponsavel,
        boolean requerAtencaoDiretoria
) implements Serializable {
}
