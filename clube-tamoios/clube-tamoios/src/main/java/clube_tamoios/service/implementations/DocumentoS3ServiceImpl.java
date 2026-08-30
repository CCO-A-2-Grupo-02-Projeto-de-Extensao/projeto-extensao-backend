package clube_tamoios.service.implementations;

import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.mapper.DocumentoMapper;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.service.ArquivoUpload;
import clube_tamoios.service.DocumentoService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@ConditionalOnProperty(name = "app.storage.type", havingValue = "s3")
public class DocumentoS3ServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PessoaRepository pessoaRepository;
    private final S3Client s3Client;
    private final String bucket;

    public DocumentoS3ServiceImpl(DocumentoRepository documentoRepository,
            PessoaRepository pessoaRepository,
            S3Client s3Client,
            @Value("${app.storage.s3.bucket}") String bucket) {
        this.documentoRepository = documentoRepository;
        this.pessoaRepository = pessoaRepository;
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public Documento salvar(ArquivoUpload arquivo, Integer idPessoa, String tipo) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + idPessoa));

        Documento doc = DocumentoMapper.toEntity(arquivo, pessoa, tipo);
        String chave = "documentos/" + UUID.randomUUID() + "-" + arquivo.nomeOriginal();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(chave)
                .contentType(arquivo.mimeType())
                .contentLength(arquivo.tamanho())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(doc.getDados()));

        doc.setDados(null);
        doc.setNomeGerado(chave);
        return documentoRepository.save(doc);
    }

    @Override
    public Documento substituir(Integer id, ArquivoUpload novoArquivo) {
        Documento doc = buscarPorId(id);

        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(doc.getNomeGerado())
                .build());

        try {
            String novaChave = "documentos/" + UUID.randomUUID() + "-" + novoArquivo.nomeOriginal();
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(novaChave)
                    .contentType(novoArquivo.mimeType())
                    .contentLength(novoArquivo.tamanho())
                    .build(), RequestBody.fromBytes(novoArquivo.conteudo()));

            doc.setNomeOriginal(novoArquivo.nomeOriginal());
            doc.setMimeType(novoArquivo.mimeType());
            doc.setTamanho(novoArquivo.tamanho());
            doc.setNomeGerado(novaChave);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao substituir arquivo no S3", e);
        }
        return documentoRepository.save(doc);
    }

    @Override
    public List<Documento> listarPorPessoa(Integer idPessoa) {
        return documentoRepository.findByPessoaIdPessoa(idPessoa);
    }

    @Override
    public Documento buscarPorId(Integer id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + id));
    }

    @Override
    public byte[] obterDados(Integer id) {
        Documento doc = buscarPorId(id);
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(doc.getNomeGerado())
                .build();
        ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
        return response.asByteArray();
    }

    @Override
    public void deletarPorId(Integer id) {
        Documento doc = buscarPorId(id);
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(doc.getNomeGerado())
                .build());
        documentoRepository.delete(doc);
    }
}
