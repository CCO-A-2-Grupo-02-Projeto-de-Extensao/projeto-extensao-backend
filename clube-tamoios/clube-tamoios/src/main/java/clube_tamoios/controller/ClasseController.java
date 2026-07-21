package clube_tamoios.controller;

import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.repository.ClasseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classes")
@Tag(name = "Classes", description = "Catálogo somente-leitura das classes do clube")
@SecurityRequirement(name = "bearerAuth")
public class ClasseController {

    private final ClasseRepository repository;

    public ClasseController(ClasseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as classes")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> classes = repository.findAll().stream()
                .map(c -> new CatalogoResponse(c.getIdClasse(), c.getNome()))
                .toList();
        return ResponseEntity.ok(classes);
    }
}
