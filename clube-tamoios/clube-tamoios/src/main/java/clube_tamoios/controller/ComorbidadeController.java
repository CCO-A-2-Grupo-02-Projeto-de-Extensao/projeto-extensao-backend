package clube_tamoios.controller;

import clube_tamoios.dto.request.ComorbidadeCadastroRequest;
import clube_tamoios.dto.response.ComorbidadeResponse;
import clube_tamoios.mapper.ComorbidadeMapper;
import clube_tamoios.service.ComorbidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comorbidades")
@Tag(name = "Comorbidades", description = "Gestão de comorbidades")
@SecurityRequirement(name = "bearerAuth")
public class ComorbidadeController {

    private final ComorbidadeService service;

    public ComorbidadeController(ComorbidadeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar comorbidade")
    public ResponseEntity<ComorbidadeResponse> cadastrar(@RequestBody @Valid ComorbidadeCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ComorbidadeMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as comorbidades")
    public ResponseEntity<List<ComorbidadeResponse>> listar() {
        return ResponseEntity.ok(ComorbidadeMapper.toResponse(service.listar()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comorbidade por ID")
    public ResponseEntity<ComorbidadeResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ComorbidadeMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar comorbidade")
    public ResponseEntity<ComorbidadeResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid ComorbidadeCadastroRequest request) {
        return ResponseEntity.ok(ComorbidadeMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar comorbidade")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
