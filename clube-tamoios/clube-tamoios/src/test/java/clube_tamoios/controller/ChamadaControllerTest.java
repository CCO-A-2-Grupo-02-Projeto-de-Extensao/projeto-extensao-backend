package clube_tamoios.controller;

import clube_tamoios.dto.request.ChamadaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Evento;
import clube_tamoios.service.ChamadaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChamadaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChamadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChamadaService service;

    @Test
    @DisplayName("Deve retornar 201 Created ao cadastrar chamada")
    void deveRetornar201AoCadastrar() throws Exception {
        ChamadaRequest request = new ChamadaRequest();
        request.setIdEvento(1);
        request.setTitulo("Chamada Matinal");
        request.setDataChamada(LocalDate.now());

        Evento evento = new Evento();
        evento.setIdEvento(1);
        evento.setNome("Acampamento");

        Chamada chamadaSalva = new Chamada();
        chamadaSalva.setIdChamada(1);
        chamadaSalva.setEvento(evento);
        chamadaSalva.setTitulo("Chamada Matinal");

        when(service.cadastrar(any(ChamadaRequest.class))).thenReturn(chamadaSalva);

        mockMvc.perform(post("/chamadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idChamada").value(1))
                .andExpect(jsonPath("$.nomeEvento").value("Acampamento"));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao listar chamadas por evento")
    void deveRetornar200AoListarPorEvento() throws Exception {
        Evento evento = new Evento();
        evento.setIdEvento(1);

        Chamada chamada = new Chamada();
        chamada.setIdChamada(10);
        chamada.setEvento(evento);

        when(service.listarPorEvento(1)).thenReturn(List.of(chamada));

        mockMvc.perform(get("/chamadas/evento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChamada").value(10));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao deletar chamada")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/chamadas/1"))
                .andExpect(status().isNoContent());
    }
}