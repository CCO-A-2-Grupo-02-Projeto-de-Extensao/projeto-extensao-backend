package clube_tamoios.controller;

import clube_tamoios.dto.request.ChamadaRequest;
import clube_tamoios.dto.response.ChamadaResponse;
import clube_tamoios.mapper.ChamadaMapper;
import clube_tamoios.service.ChamadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/chamadas")
@Tag(name = "Chamadas", description = "Gestão de chamadas dos eventos")
@SecurityRequirement(name = "bearerAuth")
public class ChamadaController {
    private final ChamadaService service;

    public ChamadaController(ChamadaService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar nova chamada vinculada a um evento")
    public ResponseEntity<ChamadaResponse> cadastrar(@RequestBody @Valid ChamadaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ChamadaMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping("/evento/{idEvento}")
    @Operation(summary = "Listar chamadas de um evento específico")
    public ResponseEntity<List<ChamadaResponse>> listarPorEvento(@PathVariable Integer idEvento) {
        return ResponseEntity.ok(ChamadaMapper.toResponse(service.listarPorEvento(idEvento)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar chamada")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}