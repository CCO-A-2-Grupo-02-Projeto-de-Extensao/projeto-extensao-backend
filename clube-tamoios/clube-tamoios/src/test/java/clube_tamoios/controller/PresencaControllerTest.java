package clube_tamoios.controller;

import clube_tamoios.dto.request.PresencaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Presenca;
import clube_tamoios.service.PresencaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PresencaController.class)
@AutoConfigureMockMvc(addFilters = false)
class PresencaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PresencaService service;

    @Test
    @DisplayName("Deve retornar 201 Created ao registrar presença")
    void deveRetornar201AoRegistrarPresenca() throws Exception {
        PresencaRequest request = new PresencaRequest();
        request.setIdChamada(1);
        request.setIdPessoa(2);
        request.setPresente(true);

        Chamada chamada = new Chamada();
        chamada.setIdChamada(1);

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(2);
        pessoa.setNome("Maria");

        Presenca presencaSalva = new Presenca();
        presencaSalva.setId(10);
        presencaSalva.setChamada(chamada);
        presencaSalva.setPessoa(pessoa);
        presencaSalva.setPresente(true);

        when(service.registrar(any(PresencaRequest.class))).thenReturn(presencaSalva);

        mockMvc.perform(post("/presencas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nomePessoa").value("Maria"))
                .andExpect(jsonPath("$.presente").value(true));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao listar presenças por chamada")
    void deveRetornar200AoListarPorChamada() throws Exception {
        Chamada chamada = new Chamada();
        chamada.setIdChamada(1);

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        Presenca presenca = new Presenca();
        presenca.setId(5);
        presenca.setChamada(chamada);
        presenca.setPessoa(pessoa);

        when(service.listarPorChamada(1)).thenReturn(List.of(presenca));

        mockMvc.perform(get("/presencas/chamada/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao atualizar presença")
    void deveRetornar200AoAtualizar() throws Exception {
        PresencaRequest request = new PresencaRequest();
        request.setIdChamada(1);
        request.setIdPessoa(1);
        request.setPresente(false); // mudando para falta

        Chamada chamada = new Chamada();
        chamada.setIdChamada(1);

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        Presenca presencaAtualizada = new Presenca();
        presencaAtualizada.setId(5);
        presencaAtualizada.setChamada(chamada);
        presencaAtualizada.setPessoa(pessoa);
        presencaAtualizada.setPresente(false);

        when(service.atualizar(eq(5), any(PresencaRequest.class))).thenReturn(presencaAtualizada);

        mockMvc.perform(put("/presencas/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presente").value(false));
    }
}