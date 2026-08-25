package clube_tamoios.controller;

import clube_tamoios.dto.request.EspecialidadeRequest;
import clube_tamoios.dto.response.EspecialidadeResponse;
import clube_tamoios.mapper.EspecialidadeMapper;
import clube_tamoios.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
@Tag(name = "Especialidades", description = "Cadastro das especialidades do clube")
@SecurityRequirement(name = "bearerAuth")
public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as especialidades")
    public ResponseEntity<List<EspecialidadeResponse>> listar() {
        return ResponseEntity.ok(EspecialidadeMapper.toResponse(service.listar()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar especialidade por ID")
    public ResponseEntity<EspecialidadeResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(EspecialidadeMapper.toResponse(service.buscarPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Cadastrar especialidade")
    public ResponseEntity<EspecialidadeResponse> cadastrar(@RequestBody @Valid EspecialidadeRequest request) {
        EspecialidadeResponse criada = EspecialidadeMapper.toResponse(service.cadastrar(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar especialidade")
    public ResponseEntity<EspecialidadeResponse> atualizar(@PathVariable Integer id,
                                                           @RequestBody @Valid EspecialidadeRequest request) {
        return ResponseEntity.ok(EspecialidadeMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar especialidade")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
