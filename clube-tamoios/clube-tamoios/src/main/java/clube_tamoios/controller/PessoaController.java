package clube_tamoios.controller;

import clube_tamoios.dto.request.PessoaCadastroRequest;
import clube_tamoios.dto.response.PessoaResponse;
import clube_tamoios.mapper.PessoaMapper;
import clube_tamoios.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas", description = "Cadastro e gestão de pessoas (administrativos, instrutores e alunos)")
@SecurityRequirement(name = "bearerAuth")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar pessoa")
    public ResponseEntity<PessoaResponse> cadastrar(@RequestBody @Valid PessoaCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PessoaMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas (ativas e inativas)")
    public ResponseEntity<List<PessoaResponse>> listar() {
        return ResponseEntity.ok(PessoaMapper.toResponse(service.listar()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID")
    public ResponseEntity<PessoaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(PessoaMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa")
    public ResponseEntity<PessoaResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid PessoaCadastroRequest request) {
        return ResponseEntity.ok(PessoaMapper.toResponse(service.atualizar(id, request)));
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar pessoa (e o usuário vinculado, se houver)")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    @Operation(summary = "Reativar pessoa (e o usuário vinculado, se houver)")
    public ResponseEntity<Void> reativar(@PathVariable Integer id) {
        service.reativar(id);
        return ResponseEntity.noContent().build();
    }
}
