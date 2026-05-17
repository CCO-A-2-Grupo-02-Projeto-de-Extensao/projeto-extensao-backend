package clube_tamoios.controller;

import clube_tamoios.dto.request.FichaMedicaCadastroRequest;
import clube_tamoios.dto.response.FichaMedicaResponse;
import clube_tamoios.mapper.FichaMedicaMapper;
import clube_tamoios.service.FichaMedicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/fichas-medicas")
@Tag(name = "Fichas Médicas", description = "Gestão de fichas médicas")
@SecurityRequirement(name = "bearerAuth")
public class FichaMedicaController {

    private final FichaMedicaService service;

    public FichaMedicaController(FichaMedicaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar ficha médica para uma pessoa",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
                { "idPessoa": 5 }
                """))
        )
    )
    public ResponseEntity<FichaMedicaResponse> criar(@RequestBody @Valid FichaMedicaCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FichaMedicaMapper.toResponse(service.criar(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ficha médica por ID")
    public ResponseEntity<FichaMedicaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(FichaMedicaMapper.toResponse(service.buscarPorId(id)));
    }

    @GetMapping("/pessoa/{idPessoa}")
    @Operation(summary = "Buscar ficha médica por pessoa")
    public ResponseEntity<FichaMedicaResponse> buscarPorPessoa(@PathVariable Integer idPessoa) {
        return ResponseEntity.ok(FichaMedicaMapper.toResponse(service.buscarPorPessoa(idPessoa)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ficha médica")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
