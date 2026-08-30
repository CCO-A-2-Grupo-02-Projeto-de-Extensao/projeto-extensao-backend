package clube_tamoios.mapper;

import clube_tamoios.dto.response.DocumentoResponse;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import org.junit.jupiter.api.Test;
import clube_tamoios.service.ArquivoUpload;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentoMapperTest {

    @Test
    void toResponse_deveMapearTodosCamposCorretamente() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        Documento doc = new Documento();
        doc.setId(10);
        doc.setPessoa(pessoa);
        doc.setNomeOriginal("laudo.pdf");
        doc.setMimeType("application/pdf");
        doc.setTamanho(2048L);
        doc.setDataUpload(LocalDateTime.of(2026, 5, 17, 10, 0));

        DocumentoResponse response = DocumentoMapper.toResponse(doc);

        assertThat(response.getId()).isEqualTo(10);
        assertThat(response.getIdPessoa()).isEqualTo(1);
        assertThat(response.getNomePessoa()).isEqualTo("Carlos Silva");
        assertThat(response.getNomeOriginal()).isEqualTo("laudo.pdf");
        assertThat(response.getMimeType()).isEqualTo("application/pdf");
        assertThat(response.getTamanho()).isEqualTo(2048L);
        assertThat(response.getDataUpload()).isEqualTo(LocalDateTime.of(2026, 5, 17, 10, 0));
    }

    @Test
    void toResponse_comPessoaNula_naoDeveMapearDadosDePessoa() {
        Documento doc = new Documento();
        doc.setId(1);
        doc.setPessoa(null);
        doc.setNomeOriginal("arquivo.pdf");
        doc.setMimeType("application/pdf");
        doc.setTamanho(512L);

        DocumentoResponse response = DocumentoMapper.toResponse(doc);

        assertThat(response.getIdPessoa()).isNull();
        assertThat(response.getNomePessoa()).isNull();
        assertThat(response.getNomeOriginal()).isEqualTo("arquivo.pdf");
    }

    @Test
    void toResponse_lista_deveMapearTodosOsElementos() {
        Documento doc1 = new Documento();
        doc1.setId(1);
        doc1.setNomeOriginal("a.pdf");
        doc1.setMimeType("application/pdf");
        doc1.setTamanho(100L);

        Documento doc2 = new Documento();
        doc2.setId(2);
        doc2.setNomeOriginal("b.jpg");
        doc2.setMimeType("image/jpeg");
        doc2.setTamanho(200L);

        List<DocumentoResponse> responses = DocumentoMapper.toResponse(List.of(doc1, doc2));

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getNomeOriginal()).isEqualTo("a.pdf");
        assertThat(responses.get(1).getNomeOriginal()).isEqualTo("b.jpg");
    }

    @Test
    void toEntity_deveMapearArquivoUploadParaDocumentoCorretamente() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        byte[] conteudo = "conteudo do arquivo de teste".getBytes();
        ArquivoUpload arquivo = new ArquivoUpload(conteudo, "laudo.pdf", "application/pdf");

        Documento doc = DocumentoMapper.toEntity(arquivo, pessoa, "laudo");

        assertThat(doc.getNomeOriginal()).isEqualTo("laudo.pdf");
        assertThat(doc.getMimeType()).isEqualTo("application/pdf");
        assertThat(doc.getTamanho()).isEqualTo((long) conteudo.length);
        assertThat(doc.getDados()).isEqualTo(conteudo);
        assertThat(doc.getPessoa()).isEqualTo(pessoa);
    }
}
