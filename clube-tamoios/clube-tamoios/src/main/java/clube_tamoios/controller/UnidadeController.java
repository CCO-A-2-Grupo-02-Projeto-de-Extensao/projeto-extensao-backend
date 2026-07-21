package clube_tamoios.controller;

import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.repository.UnidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidades")
@Tag(name = "Unidades", description = "Catálogo somente-leitura das unidades do clube")
@SecurityRequirement(name = "bearerAuth")
public class UnidadeController {

    private final UnidadeRepository repository;

    public UnidadeController(UnidadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as unidades")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> unidades = repository.findAll().stream()
                .map(u -> new CatalogoResponse(u.getIdUnidade(), u.getNome()))
                .toList();
        return ResponseEntity.ok(unidades);
    }
}
