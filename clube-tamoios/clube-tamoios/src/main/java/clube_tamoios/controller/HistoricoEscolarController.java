package clube_tamoios.controller;

import clube_tamoios.dto.request.HistoricoEscolarAtualizacaoRequest;
import clube_tamoios.dto.request.HistoricoEscolarCadastroRequest;
import clube_tamoios.dto.response.HistoricoEscolarResponse;
import clube_tamoios.mapper.HistoricoEscolarMapper;
import clube_tamoios.service.HistoricoEscolarService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historicos-escolares")
@Tag(name = "Históricos escolares", description = "Boletim e declaração dos pais por ano")
@SecurityRequirement(name = "bearerAuth")
public class HistoricoEscolarController {

    private final HistoricoEscolarService service;

    public HistoricoEscolarController(HistoricoEscolarService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar histórico escolar de um ano",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
                { "idPessoa": 1, "ano": 2026, "serie": "7º ano", "obediencia": true,
                  "prestatividade": false, "participacao": true, "idBoletim": null, "idDeclaracao": null }
                """))
        )
    )
    public ResponseEntity<HistoricoEscolarResponse> cadastrar(@RequestBody @Valid HistoricoEscolarCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HistoricoEscolarMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping("/pessoa/{idPessoa}")
    @Operation(summary = "Listar históricos escolares de uma pessoa, do ano mais recente ao mais antigo")
    public ResponseEntity<List<HistoricoEscolarResponse>> listarPorPessoa(@PathVariable Integer idPessoa) {
        return ResponseEntity.ok(HistoricoEscolarMapper.toResponse(service.listarPorPessoa(idPessoa)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar histórico escolar por ID")
    public ResponseEntity<HistoricoEscolarResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(HistoricoEscolarMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar histórico escolar")
    public ResponseEntity<HistoricoEscolarResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid HistoricoEscolarAtualizacaoRequest request) {
        return ResponseEntity.ok(HistoricoEscolarMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar histórico escolar")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
