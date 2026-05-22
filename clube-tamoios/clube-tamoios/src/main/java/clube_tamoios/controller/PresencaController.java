package clube_tamoios.controller;

import clube_tamoios.dto.request.PresencaRequest;
import clube_tamoios.dto.response.PresencaResponse;
import clube_tamoios.mapper.PresencaMapper;
import clube_tamoios.service.PresencaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/presencas")
@Tag(name = "Presenças", description = "Gestão de presença nas chamadas")
@SecurityRequirement(name = "bearerAuth")
public class PresencaController {

    private final PresencaService service;

    public PresencaController(PresencaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar presença de uma pessoa em uma chamada")
    public ResponseEntity<PresencaResponse> cadastrar(@RequestBody @Valid PresencaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PresencaMapper.toResponse(service.registrar(request)));
    }

    @GetMapping("/chamada/{idChamada}")
    @Operation(summary = "Listar todas as presenças de uma chamada")
    public ResponseEntity<List<PresencaResponse>> listarPorChamada(@PathVariable Integer idChamada) {
        return ResponseEntity.ok(PresencaMapper.toResponse(service.listarPorChamada(idChamada)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar presença por ID")
    public ResponseEntity<PresencaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(PresencaMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma presença")
    public ResponseEntity<PresencaResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid PresencaRequest request) {
        return ResponseEntity.ok(PresencaMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar presença")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}