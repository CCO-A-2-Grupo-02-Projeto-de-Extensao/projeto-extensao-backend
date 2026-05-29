package clube_tamoios.controller;

import clube_tamoios.dto.request.TurmaRequest;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Turma;
import clube_tamoios.entity.Unidade;
import clube_tamoios.service.TurmaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TurmaController.class)
@AutoConfigureMockMvc(addFilters = false)
class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TurmaService service;

    @Test
    @DisplayName("Deve retornar 201 Created ao vincular turma")
    void deveRetornar201AoCadastrarTurma() throws Exception {
        TurmaRequest request = new TurmaRequest();
        request.setIdClasse(1);
        request.setIdUnidade(2);

        Classe classe = new Classe();
        classe.setIdClasse(1);
        classe.setNome("Pioneiro");

        Unidade unidade = new Unidade();
        unidade.setIdUnidade(2);
        unidade.setNome("Tigres");

        Turma turmaSalva = new Turma();
        turmaSalva.setId(5);
        turmaSalva.setClasse(classe);
        turmaSalva.setUnidade(unidade);

        when(service.cadastrar(any(TurmaRequest.class))).thenReturn(turmaSalva);

        mockMvc.perform(post("/turmas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.idClasse").value(1))
                .andExpect(jsonPath("$.nomeUnidade").value("Tigres"));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao deletar turma")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/turmas/1"))
                .andExpect(status().isNoContent());
    }
}