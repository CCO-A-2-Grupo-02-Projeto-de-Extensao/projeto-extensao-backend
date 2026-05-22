package clube_tamoios.controller;

import clube_tamoios.dto.request.TurmaRequest;
import clube_tamoios.dto.response.TurmaResponse;
import clube_tamoios.mapper.TurmaMapper;
import clube_tamoios.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turmas", description = "Gestão de vinculação entre classes e unidades")
@SecurityRequirement(name = "bearerAuth")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Vincular uma classe a uma unidade (criar turma)")
    public ResponseEntity<TurmaResponse> cadastrar(@RequestBody @Valid TurmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TurmaMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as turmas cadastradas")
    public ResponseEntity<List<TurmaResponse>> listarTodas() {
        return ResponseEntity.ok(TurmaMapper.toResponse(service.listarTodas()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turma por ID")
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(TurmaMapper.toResponse(service.buscarPorId(id)));
    }

    @GetMapping("/classe/{idClasse}")
    @Operation(summary = "Listar unidades vinculadas a uma classe")
    public ResponseEntity<List<TurmaResponse>> listarPorClasse(@PathVariable Integer idClasse) {
        return ResponseEntity.ok(TurmaMapper.toResponse(service.listarPorClasse(idClasse)));
    }

    @GetMapping("/unidade/{idUnidade}")
    @Operation(summary = "Listar classes vinculadas a uma unidade")
    public ResponseEntity<List<TurmaResponse>> listarPorUnidade(@PathVariable Integer idUnidade) {
        return ResponseEntity.ok(TurmaMapper.toResponse(service.listarPorUnidade(idUnidade)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar a classe ou unidade de uma turma")
    public ResponseEntity<TurmaResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid TurmaRequest request) {
        return ResponseEntity.ok(TurmaMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar turma")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}