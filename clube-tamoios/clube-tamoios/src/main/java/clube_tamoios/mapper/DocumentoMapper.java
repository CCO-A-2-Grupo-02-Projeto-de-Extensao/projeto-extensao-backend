package clube_tamoios.mapper;

import clube_tamoios.dto.response.DocumentoResponse;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.service.ArquivoUpload;
import java.util.List;

public class DocumentoMapper {

    public static DocumentoResponse toResponse(Documento entity) {
        DocumentoResponse dto = new DocumentoResponse();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setNomeOriginal(entity.getNomeOriginal());
        dto.setMimeType(entity.getMimeType());
        dto.setTamanho(entity.getTamanho());
        dto.setDataUpload(entity.getDataUpload());
        if (entity.getPessoa() != null) {
            dto.setIdPessoa(entity.getPessoa().getIdPessoa());
            dto.setNomePessoa(entity.getPessoa().getNome());
        }
        return dto;
    }

    public static List<DocumentoResponse> toResponse(List<Documento> entities) {
        return entities.stream().map(DocumentoMapper::toResponse).toList();
    }

    public static Documento toEntity(ArquivoUpload arquivo, Pessoa pessoa, String tipo) {
        Documento doc = new Documento();
        doc.setPessoa(pessoa);
        doc.setTipo(tipo);
        doc.setNomeOriginal(arquivo.nomeOriginal());
        doc.setMimeType(arquivo.mimeType());
        doc.setTamanho(arquivo.tamanho());
        doc.setDados(arquivo.conteudo());
        return doc;
    }
}
