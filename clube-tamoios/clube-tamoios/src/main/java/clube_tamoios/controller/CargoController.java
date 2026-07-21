package clube_tamoios.controller;

import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.repository.CargoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargos")
@Tag(name = "Cargos", description = "Catálogo somente-leitura dos cargos do clube")
@SecurityRequirement(name = "bearerAuth")
public class CargoController {

    private final CargoRepository repository;

    public CargoController(CargoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Listar todos os cargos")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> cargos = repository.findAll().stream()
                .map(c -> new CatalogoResponse(c.getIdCargo(), c.getNome()))
                .toList();
        return ResponseEntity.ok(cargos);
    }
}
