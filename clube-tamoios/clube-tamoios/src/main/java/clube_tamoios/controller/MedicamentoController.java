package clube_tamoios.controller;

import clube_tamoios.dto.request.MedicamentoCadastroRequest;
import clube_tamoios.dto.response.MedicamentoResponse;
import clube_tamoios.mapper.MedicamentoMapper;
import clube_tamoios.service.MedicamentoService;
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
@RequestMapping("/medicamentos")
@Tag(name = "Medicamentos", description = "Gestão de medicamentos")
@SecurityRequirement(name = "bearerAuth")
public class MedicamentoController {

    private final MedicamentoService service;

    public MedicamentoController(MedicamentoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar medicamento")
    public ResponseEntity<MedicamentoResponse> cadastrar(@RequestBody @Valid MedicamentoCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MedicamentoMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todos os medicamentos")
    public ResponseEntity<List<MedicamentoResponse>> listar() {
        return ResponseEntity.ok(MedicamentoMapper.toResponse(service.listar()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID")
    public ResponseEntity<MedicamentoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(MedicamentoMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento")
    public ResponseEntity<MedicamentoResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid MedicamentoCadastroRequest request) {
        return ResponseEntity.ok(MedicamentoMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar medicamento")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
