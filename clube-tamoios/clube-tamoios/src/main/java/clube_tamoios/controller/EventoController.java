package clube_tamoios.controller;

import clube_tamoios.dto.request.EventoRequest;
import clube_tamoios.dto.response.EventoResponse;
import clube_tamoios.mapper.EventoMapper;
import clube_tamoios.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@Tag(name = "Eventos", description = "Gestão de eventos")
@SecurityRequirement(name = "bearerAuth")
public class EventoController {
    private final EventoService service;

    public EventoController(EventoService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar novo evento")
    public ResponseEntity<EventoResponse> cadastrar(@RequestBody @Valid EventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(EventoMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todos os eventos")
    public ResponseEntity<List<EventoResponse>> listar() {
        return ResponseEntity.ok(EventoMapper.toResponse(service.listar()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<EventoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(EventoMapper.toResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}