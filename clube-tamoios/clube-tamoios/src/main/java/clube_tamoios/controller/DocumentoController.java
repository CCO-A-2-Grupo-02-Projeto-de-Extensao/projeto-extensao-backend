package clube_tamoios.controller;

import clube_tamoios.dto.response.DocumentoResponse;
import clube_tamoios.entity.Documento;
import clube_tamoios.mapper.DocumentoMapper;
import clube_tamoios.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documentos")
@Tag(name = "Documentos", description = "Upload e gestão de documentos de pessoas")
@SecurityRequirement(name = "bearerAuth")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Fazer upload de documento para uma pessoa")
    public ResponseEntity<DocumentoResponse> upload(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("idPessoa") Integer idPessoa,
            @RequestParam(value = "tipo", required = false) String tipo) {
        Documento salvo = documentoService.salvar(arquivo, idPessoa, tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(DocumentoMapper.toResponse(salvo));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Substituir arquivo de um documento")
    public ResponseEntity<DocumentoResponse> substituir(
            @PathVariable Integer id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        Documento atualizado = documentoService.substituir(id, arquivo);
        return ResponseEntity.ok(DocumentoMapper.toResponse(atualizado));
    }

    @GetMapping("/pessoa/{idPessoa}")
    @Operation(summary = "Listar documentos de uma pessoa")
    public ResponseEntity<List<DocumentoResponse>> listarPorPessoa(@PathVariable Integer idPessoa) {
        List<Documento> docs = documentoService.listarPorPessoa(idPessoa);
        return ResponseEntity.ok(DocumentoMapper.toResponse(docs));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar metadados de um documento")
    public ResponseEntity<DocumentoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(DocumentoMapper.toResponse(documentoService.buscarPorId(id)));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Baixar arquivo do documento")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) {
        Documento doc = documentoService.buscarPorId(id);
        byte[] dados = documentoService.obterDados(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(doc.getMimeType()));
        headers.setContentLength(doc.getTamanho());
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(doc.getNomeOriginal())
                .build());

        return ResponseEntity.ok().headers(headers).body(dados);
    }

    @GetMapping("/{id}/visualizar")
    @Operation(summary = "Visualizar arquivo do documento no navegador")
    public ResponseEntity<byte[]> visualizar(@PathVariable Integer id) {
        Documento doc = documentoService.buscarPorId(id);
        byte[] dados = documentoService.obterDados(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(doc.getMimeType()));
        headers.setContentLength(doc.getTamanho());
        headers.setContentDisposition(ContentDisposition.inline()
                .filename(doc.getNomeOriginal())
                .build());

        return ResponseEntity.ok().headers(headers).body(dados);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar documento")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        documentoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
