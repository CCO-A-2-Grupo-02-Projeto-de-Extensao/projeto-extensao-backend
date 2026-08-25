package clube_tamoios.controller;

import clube_tamoios.dto.request.UnidadeRequest;
import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.dto.response.UnidadeDetalheResponse;
import clube_tamoios.entity.Unidade;
import clube_tamoios.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidades")
@Tag(name = "Unidades", description = "Unidades do clube")
@SecurityRequirement(name = "bearerAuth")
public class UnidadeController {

    private final UnidadeService service;

    public UnidadeController(UnidadeService service) {
        this.service = service;
    }

    // Mantém o formato CatalogoResponse (id + nome): é o que os dropdowns do
    // cadastro de desbravador consomem. Os campos novos ficam em /detalhes.
    @GetMapping
    @Operation(summary = "Listar todas as unidades")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> unidades = service.listar().stream()
                .map(u -> new CatalogoResponse(u.getIdUnidade(), u.getNome()))
                .toList();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/detalhes")
    @Operation(summary = "Listar unidades com faixa etária, sexo, conselheiro e total de desbravadores")
    public ResponseEntity<List<UnidadeDetalheResponse>> listarDetalhado() {
        return ResponseEntity.ok(service.listarDetalhado());
    }

    @PostMapping
    @Operation(summary = "Cadastrar unidade")
    public ResponseEntity<UnidadeDetalheResponse> cadastrar(@RequestBody @Valid UnidadeRequest request) {
        Unidade criada = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.toDetalhe(criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar unidade")
    public ResponseEntity<UnidadeDetalheResponse> atualizar(@PathVariable Integer id,
                                                            @RequestBody @Valid UnidadeRequest request) {
        return ResponseEntity.ok(service.toDetalhe(service.atualizar(id, request)));
    }
}
