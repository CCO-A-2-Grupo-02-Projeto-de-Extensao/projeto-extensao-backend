package clube_tamoios.controller;

import clube_tamoios.dto.request.EspecialidadeConcluidaRequest;
import clube_tamoios.dto.response.EspecialidadeConcluidaResponse;
import clube_tamoios.mapper.EspecialidadeConcluidaMapper;
import clube_tamoios.service.EspecialidadeConcluidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especialidades-concluidas")
@Tag(name = "Especialidades concluídas", description = "Especialidades que cada pessoa já concluiu, com o ano")
@SecurityRequirement(name = "bearerAuth")
public class EspecialidadeConcluidaController {

    private final EspecialidadeConcluidaService service;

    public EspecialidadeConcluidaController(EspecialidadeConcluidaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar especialidade concluída",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
                { "idPessoa": 1, "idEspecialidade": 22, "ano": 2026 }
                """))
        )
    )
    public ResponseEntity<EspecialidadeConcluidaResponse> cadastrar(@RequestBody @Valid EspecialidadeConcluidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EspecialidadeConcluidaMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping("/pessoa/{idPessoa}")
    @Operation(summary = "Listar especialidades concluídas por uma pessoa")
    public ResponseEntity<List<EspecialidadeConcluidaResponse>> listarPorPessoa(@PathVariable Integer idPessoa) {
        return ResponseEntity.ok(EspecialidadeConcluidaMapper.toResponse(service.listarPorPessoa(idPessoa)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desfazer especialidade concluída")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
