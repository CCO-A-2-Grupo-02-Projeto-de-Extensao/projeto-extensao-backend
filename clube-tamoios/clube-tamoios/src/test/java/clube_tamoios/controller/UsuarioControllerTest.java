package clube_tamoios.controller;

import clube_tamoios.dto.request.LoginRequest;
import clube_tamoios.dto.request.UsuarioAtualizacaoRequest;
import clube_tamoios.dto.request.UsuarioCadastroRequest;
import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.dto.response.UsuarioResponse;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    private UsuarioResponse responseBase() {
        UsuarioResponse r = new UsuarioResponse();
        r.setIdUsuario(1);
        r.setEmail("joao@clube.com");
        r.setAtivo(true);
        r.setNomePessoa("João Silva");
        r.setNomeCargo("Diretor");
        return r;
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao fazer login")
    void deveRetornar200AoFazerLogin() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("joao@clube.com");
        request.setSenha("senha123");

        LoginResponse response = new LoginResponse();
        response.setIdUsuario(1);
        response.setEmail("joao@clube.com");
        response.setToken("jwt-token-mock");

        when(usuarioService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@clube.com"))
                .andExpect(jsonPath("$.token").value("jwt-token-mock"));
    }

    @Test
    @DisplayName("Deve retornar 201 Created ao cadastrar usuário")
    void deveRetornar201AoCadastrar() throws Exception {
        UsuarioCadastroRequest request = new UsuarioCadastroRequest();
        request.setEmail("joao@clube.com");
        request.setSenha("senha123");
        request.setIdPessoa(1);
        request.setIdCargo(1);

        when(usuarioService.cadastrar(any(UsuarioCadastroRequest.class))).thenReturn(responseBase());

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.email").value("joao@clube.com"));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao listar usuários ativos")
    void deveRetornar200AoListarAtivos() throws Exception {
        when(usuarioService.listarAtivos()).thenReturn(List.of(responseBase()));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content quando não há usuários ativos")
    void deveRetornar204QuandoListaAtivosVazia() throws Exception {
        when(usuarioService.listarAtivos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao listar todos os usuários")
    void deveRetornar200AoListarTodos() throws Exception {
        when(usuarioService.listarTodos()).thenReturn(List.of(responseBase()));

        mockMvc.perform(get("/usuarios/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("joao@clube.com"));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content quando lista de todos está vazia")
    void deveRetornar204QuandoListaTodosVazia() throws Exception {
        when(usuarioService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios/todos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao buscar usuário por ID")
    void deveRetornar200AoBuscarPorId() throws Exception {
        when(usuarioService.buscarPorId(1)).thenReturn(responseBase());

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao atualizar usuário")
    void deveRetornar200AoAtualizar() throws Exception {
        UsuarioAtualizacaoRequest request = new UsuarioAtualizacaoRequest();
        request.setEmail("joao@clube.com");
        request.setIdCargo(1);

        when(usuarioService.atualizar(eq(1), any(UsuarioAtualizacaoRequest.class))).thenReturn(responseBase());

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao desativar usuário")
    void deveRetornar204AoDesativar() throws Exception {
        doNothing().when(usuarioService).desativar(1);

        mockMvc.perform(patch("/usuarios/1/desativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao deletar usuário")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(usuarioService).deletar(1);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}