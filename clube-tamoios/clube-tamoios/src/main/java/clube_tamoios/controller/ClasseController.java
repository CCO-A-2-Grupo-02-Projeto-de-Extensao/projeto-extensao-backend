package clube_tamoios.controller;

import clube_tamoios.dto.request.VincularEspecialidadesRequest;
import clube_tamoios.dto.response.CatalogoResponse;
import clube_tamoios.dto.response.EspecialidadeResponse;
import clube_tamoios.dto.response.PessoaResponse;
import clube_tamoios.dto.response.UnidadeDetalheResponse;
import clube_tamoios.entity.Classe;
import clube_tamoios.mapper.EspecialidadeMapper;
import clube_tamoios.mapper.PessoaMapper;
import clube_tamoios.service.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
@Tag(name = "Classes", description = "Classes do clube e seus vínculos com pessoas, especialidades e unidades")
@SecurityRequirement(name = "bearerAuth")
public class ClasseController {

    private final ClasseService service;

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as classes")
    public ResponseEntity<List<CatalogoResponse>> listar() {
        List<CatalogoResponse> classes = service.listar().stream()
                .map(c -> new CatalogoResponse(c.getIdClasse(), c.getNome()))
                .toList();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{idClasse}")
    @Operation(summary = "Buscar classe por ID")
    public ResponseEntity<CatalogoResponse> buscarPorId(@PathVariable Integer idClasse) {
        Classe classe = service.buscarPorId(idClasse);
        return ResponseEntity.ok(new CatalogoResponse(classe.getIdClasse(), classe.getNome()));
    }

    @GetMapping("/{idClasse}/participantes")
    @Operation(summary = "Listar as pessoas ativas vinculadas a uma classe")
    public ResponseEntity<List<PessoaResponse>> listarParticipantes(@PathVariable Integer idClasse) {
        List<PessoaResponse> participantes = service.listarParticipantes(idClasse).stream()
                .map(PessoaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{idClasse}/especialidades")
    @Operation(summary = "Listar as especialidades vinculadas a uma classe")
    public ResponseEntity<List<EspecialidadeResponse>> listarEspecialidades(@PathVariable Integer idClasse) {
        return ResponseEntity.ok(
                EspecialidadeMapper.toResponse(service.listarEspecialidades(idClasse)));
    }

    @PostMapping("/{idClasse}/especialidades")
    @Operation(summary = "Vincular especialidades a uma classe")
    public ResponseEntity<List<EspecialidadeResponse>> vincularEspecialidades(
            @PathVariable Integer idClasse,
            @RequestBody @Valid VincularEspecialidadesRequest request) {
        return ResponseEntity.ok(EspecialidadeMapper.toResponse(
                service.vincularEspecialidades(idClasse, request.getIdsEspecialidades())));
    }

    @DeleteMapping("/{idClasse}/especialidades/{idEspecialidade}")
    @Operation(summary = "Desvincular uma especialidade de uma classe")
    public ResponseEntity<Void> desvincularEspecialidade(@PathVariable Integer idClasse,
                                                         @PathVariable Integer idEspecialidade) {
        service.desvincularEspecialidade(idClasse, idEspecialidade);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idClasse}/unidades")
    @Operation(summary = "Listar as unidades vinculadas a uma classe, com conselheiro e total de desbravadores")
    public ResponseEntity<List<UnidadeDetalheResponse>> listarUnidades(@PathVariable Integer idClasse) {
        return ResponseEntity.ok(service.listarUnidades(idClasse));
    }
}
