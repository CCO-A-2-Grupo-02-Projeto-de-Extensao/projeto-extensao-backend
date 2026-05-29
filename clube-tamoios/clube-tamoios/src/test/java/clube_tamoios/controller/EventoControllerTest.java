package clube_tamoios.controller;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.entity.Evento;
import clube_tamoios.service.EventoService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
@AutoConfigureMockMvc(addFilters = false) // Ignora a autenticação JWT para o teste
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventoService service;

    @Test
    @DisplayName("Deve retornar 201 Created ao cadastrar evento válido")
    void deveRetornar201AoCadastrar() throws Exception {
        EventoRequest request = new EventoRequest();
        request.setNome("Campori");
        request.setTipo("Acampamento");

        Evento eventoSalvo = new Evento();
        eventoSalvo.setIdEvento(1);
        eventoSalvo.setNome("Campori");

        when(service.cadastrar(any(EventoRequest.class))).thenReturn(eventoSalvo);

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEvento").value(1))
                .andExpect(jsonPath("$.nome").value("Campori"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se nome do evento for vazio")
    void deveRetornar400SeNomeVazio() throws Exception {
        EventoRequest request = new EventoRequest();
        // Não informando o nome para forçar erro de @Valid

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao listar eventos")
    void deveRetornar200AoListar() throws Exception {
        Evento evento = new Evento();
        evento.setIdEvento(1);
        evento.setNome("Reunião Regular");

        when(service.listar()).thenReturn(List.of(evento));

        mockMvc.perform(get("/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEvento").value(1))
                .andExpect(jsonPath("$[0].nome").value("Reunião Regular"));
    }
}