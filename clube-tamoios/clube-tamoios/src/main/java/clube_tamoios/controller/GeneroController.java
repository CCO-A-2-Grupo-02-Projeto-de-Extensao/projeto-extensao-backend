package clube_tamoios.controller;

import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.repository.GeneroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generos")
@Tag(name = "Gêneros", description = "Catálogo somente-leitura dos gêneros")
@SecurityRequirement(name = "bearerAuth")
public class GeneroController {

    private final GeneroRepository repository;

    public GeneroController(GeneroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todos os gêneros")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> generos = repository.findAll().stream()
                .map(g -> new CatalogoResponse(g.getIdGenero(), g.getNome()))
                .toList();
        return ResponseEntity.ok(generos);
    }
}
