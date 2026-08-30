package clube_tamoios.controller;

import clube_tamoios.config.SecurityConfig;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.security.JwtTokenService;
import clube_tamoios.service.TokenService;
import clube_tamoios.service.DocumentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentoController.class)
@Import({SecurityConfig.class, JwtTokenService.class})
class DocumentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private DocumentoService documentoService;

    private String token;
    private Documento documento;

    @BeforeEach
    void setUp() {
        token = tokenService.gerar("test@teste.com", "DIRETOR");

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        documento = new Documento();
        documento.setId(1);
        documento.setPessoa(pessoa);
        documento.setNomeOriginal("laudo.pdf");
        documento.setMimeType("application/pdf");
        documento.setTamanho(1024L);
        documento.setDados("conteudo".getBytes());
        documento.setDataUpload(LocalDateTime.of(2026, 5, 17, 10, 0));
    }

    @Test
    void upload_deveRetornar201_quandoArquivoValido() throws Exception {
        MockMultipartFile arquivo = new MockMultipartFile(
                "arquivo", "laudo.pdf", "application/pdf", "conteudo".getBytes());

        when(documentoService.salvar(any(), eq(1), any())).thenReturn(documento);

        mockMvc.perform(multipart("/documentos")
                        .file(arquivo)
                        .param("idPessoa", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeOriginal").value("laudo.pdf"))
                .andExpect(jsonPath("$.mimeType").value("application/pdf"))
                .andExpect(jsonPath("$.tamanho").value(1024));
    }

    @Test
    void upload_deveRetornar401_quandoSemToken() throws Exception {
        MockMultipartFile arquivo = new MockMultipartFile(
                "arquivo", "laudo.pdf", "application/pdf", "conteudo".getBytes());

        mockMvc.perform(multipart("/documentos")
                        .file(arquivo)
                        .param("idPessoa", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listarPorPessoa_deveRetornar200_comListaDeDocumentos() throws Exception {
        when(documentoService.listarPorPessoa(1)).thenReturn(List.of(documento));

        mockMvc.perform(get("/documentos/pessoa/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeOriginal").value("laudo.pdf"))
                .andExpect(jsonPath("$[0].idPessoa").value(1));
    }

    @Test
    void listarPorPessoa_deveRetornar200_comListaVazia() throws Exception {
        when(documentoService.listarPorPessoa(99)).thenReturn(List.of());

        mockMvc.perform(get("/documentos/pessoa/99")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void buscarPorId_deveRetornar200_quandoExistente() throws Exception {
        when(documentoService.buscarPorId(1)).thenReturn(documento);

        mockMvc.perform(get("/documentos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeOriginal").value("laudo.pdf"));
    }

    @Test
    void download_deveRetornarBytesComHeaderAttachment() throws Exception {
        byte[] conteudo = "conteudo do pdf".getBytes();
        when(documentoService.buscarPorId(1)).thenReturn(documento);
        when(documentoService.obterDados(1)).thenReturn(conteudo);

        mockMvc.perform(get("/documentos/1/download")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", containsString("attachment")))
                .andExpect(header().string("Content-Disposition", containsString("laudo.pdf")))
                .andExpect(content().bytes(conteudo));
    }

    @Test
    void visualizar_deveRetornarBytesComHeaderInline() throws Exception {
        byte[] conteudo = "conteudo do pdf".getBytes();
        when(documentoService.buscarPorId(1)).thenReturn(documento);
        when(documentoService.obterDados(1)).thenReturn(conteudo);

        mockMvc.perform(get("/documentos/1/visualizar")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", containsString("inline")))
                .andExpect(header().string("Content-Disposition", containsString("laudo.pdf")))
                .andExpect(content().bytes(conteudo));
    }

    @Test
    void deletar_deveRetornar204_quandoExistente() throws Exception {
        doNothing().when(documentoService).deletarPorId(1);

        mockMvc.perform(delete("/documentos/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_deveRetornar401_quandoSemToken() throws Exception {
        mockMvc.perform(delete("/documentos/1"))
                .andExpect(status().isUnauthorized());
    }
}
