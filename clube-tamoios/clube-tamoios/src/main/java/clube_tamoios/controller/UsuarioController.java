package clube_tamoios.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import clube_tamoios.dto.request.LoginRequest;
import clube_tamoios.dto.request.UsuarioAtualizacaoRequest;
import clube_tamoios.dto.request.UsuarioCadastroRequest;
import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.dto.response.UsuarioResponse;
import clube_tamoios.service.UsuarioService;

import java.util.List;

// @Tag agrupa todos os endpoints sob o nome "Desbravadores" no Swagger UI
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Desbravadores", description = "Gerenciamento dos desbravadores e usuários do sistema Arandu Digital")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Login do desbravador", description = "Autentica um desbravador/usuário no sistema e retorna o token de acesso")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @Operation(summary = "Cadastrar desbravador", description = "Registra um novo desbravador no sistema Arandu Digital")
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(request));
    }

    @Operation(summary = "Listar desbravadores ativos", description = "Retorna a lista de todos os desbravadores com status ativo")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarAtivos() {
        List<UsuarioResponse> usuarios = usuarioService.listarAtivos();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Listar todos os desbravadores", description = "Retorna a lista completa de desbravadores, incluindo os inativos")
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar desbravador por ID", description = "Retorna os dados de um desbravador específico pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar dados do desbravador", description = "Atualiza as informações cadastrais de um desbravador")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioAtualizacaoRequest request) {
        return ResponseEntity.ok(usuarioService.atualizar(id, request));
    }

    @Operation(summary = "Desativar desbravador", description = "Desativa o cadastro de um desbravador sem excluí-lo do sistema")
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Excluir desbravador", description = "Remove permanentemente o cadastro de um desbravador do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

