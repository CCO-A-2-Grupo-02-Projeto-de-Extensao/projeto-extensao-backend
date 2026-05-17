package clube_tamoios.controller;

import clube_tamoios.dto.request.MedicacaoAtualizacaoRequest;
import clube_tamoios.dto.request.MedicacaoCadastroRequest;
import clube_tamoios.dto.response.MedicacaoResponse;
import clube_tamoios.mapper.MedicacaoMapper;
import clube_tamoios.service.MedicacaoService;
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
@RequestMapping("/medicacoes")
@Tag(name = "Medicações", description = "Gestão de medicações das fichas médicas")
@SecurityRequirement(name = "bearerAuth")
public class MedicacaoController {

    private final MedicacaoService service;

    public MedicacaoController(MedicacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Adicionar medicação a uma ficha médica")
    public ResponseEntity<MedicacaoResponse> cadastrar(@RequestBody @Valid MedicacaoCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MedicacaoMapper.toResponse(service.cadastrar(request)));
    }

    @GetMapping("/ficha/{idFichaMedica}")
    @Operation(summary = "Listar medicações de uma ficha médica")
    public ResponseEntity<List<MedicacaoResponse>> listarPorFicha(@PathVariable Integer idFichaMedica) {
        return ResponseEntity.ok(MedicacaoMapper.toResponse(service.listarPorFicha(idFichaMedica)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicação por ID")
    public ResponseEntity<MedicacaoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(MedicacaoMapper.toResponse(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação")
    public ResponseEntity<MedicacaoResponse> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid MedicacaoAtualizacaoRequest request) {
        return ResponseEntity.ok(MedicacaoMapper.toResponse(service.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar medicação")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
