package clube_tamoios.controller;

import clube_tamoios.dto.request.OcorrenciaCadastroRequest;
import clube_tamoios.dto.response.OcorrenciaResponse;
import clube_tamoios.mapper.OcorrenciaMapper;
import clube_tamoios.service.OcorrenciaService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocorrencias")
@Tag(name = "Ocorrências", description = "Gestão de ocorrências de pessoas")
@SecurityRequirement(name = "bearerAuth")
public class OcorrenciaController {

    private final OcorrenciaService service;

    public OcorrenciaController(OcorrenciaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar ocorrência",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
                { "data": "2026-05-22", "descricao": "Briga durante atividade", "idPessoa": 1 }
                """))
        )
    )
    public ResponseEntity<OcorrenciaResponse> cadastrar(@RequestBody @Valid OcorrenciaCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OcorrenciaMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar todas as ocorrências")
    public ResponseEntity<List<OcorrenciaResponse>> listar() {
        return ResponseEntity.ok(OcorrenciaMapper.toResponse(service.listar()));
    }

    @GetMapping("/pessoa/{idPessoa}")
    @Operation(summary = "Listar ocorrências por pessoa")
    public ResponseEntity<List<OcorrenciaResponse>> listarPorPessoa(@PathVariable Integer idPessoa) {
        return ResponseEntity.ok(OcorrenciaMapper.toResponse(service.listarPorPessoa(idPessoa)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ocorrência por ID")
    public ResponseEntity<OcorrenciaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(OcorrenciaMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ocorrência")
    public ResponseEntity<OcorrenciaResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid OcorrenciaCadastroRequest request) {
        return ResponseEntity.ok(OcorrenciaMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ocorrência")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
