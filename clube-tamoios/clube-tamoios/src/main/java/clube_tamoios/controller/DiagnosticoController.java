package clube_tamoios.controller;

import clube_tamoios.dto.request.DiagnosticoAtualizacaoRequest;
import clube_tamoios.dto.request.DiagnosticoCadastroRequest;
import clube_tamoios.dto.response.DiagnosticoResponse;
import clube_tamoios.mapper.DiagnosticoMapper;
import clube_tamoios.service.DiagnosticoService;
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
@RequestMapping("/diagnosticos")
@Tag(name = "Diagnósticos", description = "Gestão de diagnósticos das fichas médicas")
@SecurityRequirement(name = "bearerAuth")
public class DiagnosticoController {

    private final DiagnosticoService service;

    public DiagnosticoController(DiagnosticoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Adicionar diagnóstico a uma ficha médica")
    public ResponseEntity<DiagnosticoResponse> cadastrar(@RequestBody @Valid DiagnosticoCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DiagnosticoMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping("/ficha/{idFichaMedica}")
    @Operation(summary = "Listar diagnósticos de uma ficha médica")
    public ResponseEntity<List<DiagnosticoResponse>> listarPorFicha(@PathVariable Integer idFichaMedica) {
        return ResponseEntity.ok(DiagnosticoMapper.toResponse(service.listarPorFicha(idFichaMedica)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar diagnóstico por ID")
    public ResponseEntity<DiagnosticoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(DiagnosticoMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar diagnóstico")
    public ResponseEntity<DiagnosticoResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid DiagnosticoAtualizacaoRequest request) {
        return ResponseEntity.ok(DiagnosticoMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar diagnóstico")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
