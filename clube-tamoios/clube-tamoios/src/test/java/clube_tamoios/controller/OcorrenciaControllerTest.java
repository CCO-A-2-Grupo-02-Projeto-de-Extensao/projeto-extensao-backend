package clube_tamoios.controller;

import clube_tamoios.config.SecurityConfig;
import clube_tamoios.entity.Ocorrencia;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.security.JwtUtil;
import clube_tamoios.service.OcorrenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OcorrenciaController.class)
@Import(SecurityConfig.class)
class OcorrenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OcorrenciaService service;

    private String token;
    private Ocorrencia ocorrencia;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        token = JwtUtil.gerarToken("test@teste.com");
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        ocorrencia = new Ocorrencia();
        ocorrencia.setId(1);
        ocorrencia.setData(LocalDate.of(2026, 5, 22));
        ocorrencia.setDescricao("Briga durante atividade");
        ocorrencia.setPessoa(pessoa);
    }

    @Test
    void cadastrar_deveRetornar201_quandoDadosValidos() throws Exception {
        when(service.cadastrar(any())).thenReturn(ocorrencia);

        String body = """
                { "data": "2026-05-22", "descricao": "Briga durante atividade", "idPessoa": 1 }
                """;

        mockMvc.perform(post("/ocorrencias")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descricao").value("Briga durante atividade"))
                .andExpect(jsonPath("$.idPessoa").value(1))
                .andExpect(jsonPath("$.nomePessoa").value("Carlos Silva"));
    }

    @Test
    void cadastrar_deveRetornar401_quandoSemToken() throws Exception {
        String body = """
                { "data": "2026-05-22", "descricao": "Briga durante atividade", "idPessoa": 1 }
                """;

        mockMvc.perform(post("/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void cadastrar_deveRetornar400_quandoCamposObrigatoriosFaltando() throws Exception {
        String body = "{}";

        mockMvc.perform(post("/ocorrencias")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listar_deveRetornar200_comListaDeOcorrencias() throws Exception {
        when(service.listar()).thenReturn(List.of(ocorrencia));

        mockMvc.perform(get("/ocorrencias")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].descricao").value("Briga durante atividade"))
                .andExpect(jsonPath("$[0].idPessoa").value(1));
    }

    @Test
    void listar_deveRetornar200_comListaVazia() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mockMvc.perform(get("/ocorrencias")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void listarPorPessoa_deveRetornar200_comOcorrenciasDaPessoa() throws Exception {
        when(service.listarPorPessoa(1)).thenReturn(List.of(ocorrencia));

        mockMvc.perform(get("/ocorrencias/pessoa/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].idPessoa").value(1));
    }

    @Test
    void buscarPorId_deveRetornar200_quandoExistente() throws Exception {
        when(service.buscarPorId(1)).thenReturn(ocorrencia);

        mockMvc.perform(get("/ocorrencias/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descricao").value("Briga durante atividade"));
    }

    @Test
    void buscarPorId_deveRetornar404_quandoNaoEncontrado() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new EntidadeNaoEncontradaException("Ocorrência não encontrada: 99"));

        mockMvc.perform(get("/ocorrencias/99")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_deveRetornar200_quandoDadosValidos() throws Exception {
        when(service.atualizar(eq(1), any())).thenReturn(ocorrencia);

        String body = """
                { "data": "2026-05-22", "descricao": "Descrição atualizada", "idPessoa": 1 }
                """;

        mockMvc.perform(put("/ocorrencias/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void atualizar_deveRetornar404_quandoNaoEncontrado() throws Exception {
        when(service.atualizar(eq(99), any())).thenThrow(new EntidadeNaoEncontradaException("Ocorrência não encontrada: 99"));

        String body = """
                { "data": "2026-05-22", "descricao": "Descrição atualizada", "idPessoa": 1 }
                """;

        mockMvc.perform(put("/ocorrencias/99")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_deveRetornar204_quandoExistente() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/ocorrencias/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_deveRetornar404_quandoNaoEncontrado() throws Exception {
        doThrow(new EntidadeNaoEncontradaException("Ocorrência não encontrada: 99"))
                .when(service).deletar(99);

        mockMvc.perform(delete("/ocorrencias/99")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar_deveRetornar401_quandoSemToken() throws Exception {
        mockMvc.perform(delete("/ocorrencias/1"))
                .andExpect(status().isUnauthorized());
    }
}
