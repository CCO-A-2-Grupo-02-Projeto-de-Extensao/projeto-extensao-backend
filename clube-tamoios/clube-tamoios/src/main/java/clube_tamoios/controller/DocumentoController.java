package clube_tamoios.controller;

import clube_tamoios.dto.DocumentoAtualizadoMessage;
import clube_tamoios.dto.request.EnquadramentoRequest;
import clube_tamoios.dto.response.DocumentoResponse;
import clube_tamoios.entity.Documento;
import clube_tamoios.mapper.DocumentoMapper;
import clube_tamoios.producer.AvisoProducer;
import clube_tamoios.service.ArquivoUpload;
import clube_tamoios.service.DocumentoService;
import clube_tamoios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final AvisoProducer avisoProducer;
    private final UsuarioService usuarioService;

    public DocumentoController(DocumentoService documentoService, AvisoProducer avisoProducer,  UsuarioService usuarioService) {
        this.documentoService = documentoService;
        this.avisoProducer = avisoProducer;
        this.usuarioService = usuarioService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Fazer upload de documento para uma pessoa")
    public ResponseEntity<DocumentoResponse> upload(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("idPessoa") Integer idPessoa,
            @RequestParam(value = "tipo", required = false) String tipo) {
        Documento salvo = documentoService.salvar(paraUpload(arquivo), idPessoa, tipo);
        avisoProducer.enviarDocumentoAtualizado(criarEventoDocumentoAtualizado(salvo, "upload"));
        return ResponseEntity.status(HttpStatus.CREATED).body(DocumentoMapper.toResponse(salvo));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Substituir arquivo de um documento")
    public ResponseEntity<DocumentoResponse> substituir(
            @PathVariable Integer id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        Documento atualizado = documentoService.substituir(id, paraUpload(arquivo));
        avisoProducer.enviarDocumentoAtualizado(criarEventoDocumentoAtualizado(atualizado, "substituicao"));
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

    @PatchMapping("/{id}/enquadramento")
    @Operation(summary = "Ajustar o enquadramento da imagem (posição e zoom)")
    public ResponseEntity<DocumentoResponse> ajustarEnquadramento(
            @PathVariable Integer id,
            @Valid @RequestBody EnquadramentoRequest request) {
        Documento atualizado = documentoService.atualizarEnquadramento(
                id, request.getPosX(), request.getPosY(), request.getZoom());
        return ResponseEntity.ok(DocumentoMapper.toResponse(atualizado));
    }

    private DocumentoAtualizadoMessage criarEventoDocumentoAtualizado(Documento documento, String tipoOperacao) {
        String nomePessoa = documento.getPessoa() != null ? documento.getPessoa().getNome() : "Desbravador";
        String tipoDocumento = documento.getTipo() != null ? documento.getTipo() : "documento";
        String tipoDocumentoFormatado = traduzTipoDocumento(tipoDocumento);
        String listaDiretoria = usuarioService.listarAtivos().stream()
                .map(usuario -> usuario.getNomePessoa() + " - " + usuario.getEmail())
                .collect(Collectors.joining(", "));

        String mensagemResponsavel = String.format(
                "%s atualizou a %s do desbravador %s. Revise as informações antes de prosseguir.",
                "upload".equals(tipoOperacao) ? "Secretária" : "Secretária",
                tipoDocumentoFormatado,
                nomePessoa
        );
        String mensagemDiretoria = String.format(
                "Diretoria: a %s do desbravador %s foi %s no sistema. Verificação necessária e se necessário, acionar os responsáveis e outros membros da diretoria.",
                listaDiretoria,
                tipoDocumentoFormatado,
                nomePessoa,
                "upload".equals(tipoOperacao) ? "atualizada" : "substituída"
        );

        return new DocumentoAtualizadoMessage(
                documento.getPessoa() != null ? documento.getPessoa().getIdPessoa() : null,
                nomePessoa,
                documento.getId(),
                tipoDocumento,
                documento.getNomeOriginal(),
                LocalDateTime.now(),
                listaDiretoria,
                tipoOperacao,
                mensagemResponsavel,
                "responsavel",
                true,
                true
        );
    }

    private String traduzTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null) return "documento";
        return switch (tipoDocumento) {
            case "fichaMedica" -> "ficha médica";
            case "foto" -> "foto";
            default -> tipoDocumento;
        };
    }

    private static ArquivoUpload paraUpload(MultipartFile arquivo) {
        try {
            return new ArquivoUpload(arquivo.getBytes(),
                    arquivo.getOriginalFilename(),
                    arquivo.getContentType());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler bytes do arquivo", e);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar documento")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        documentoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
