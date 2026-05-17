package clube_tamoios.service.implementations;

import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.mapper.DocumentoMapper;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.service.DocumentoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@ConditionalOnProperty(name = "app.storage.type", havingValue = "local")
public class DocumentoLocalServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PessoaRepository pessoaRepository;
    private final Path pastaBase;

    public DocumentoLocalServiceImpl(DocumentoRepository documentoRepository,
            PessoaRepository pessoaRepository,
            @Value("${app.storage.local.path:uploads}") String pastaBase) {
        this.documentoRepository = documentoRepository;
        this.pessoaRepository = pessoaRepository;
        this.pastaBase = Paths.get(pastaBase);
    }

    @Override
    public Documento salvar(MultipartFile arquivo, Integer idPessoa) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + idPessoa));

        Documento doc = DocumentoMapper.toEntity(arquivo, pessoa);
        String nomeFisico = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path destino = pastaBase.resolve(nomeFisico);

        try {
            Files.createDirectories(pastaBase);
            Files.write(destino, doc.getDados());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo em disco", e);
        }

        doc.setDados(null);
        doc.setNomeGerado(destino.toString());
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
        try {
            return Files.readAllBytes(Paths.get(doc.getNomeGerado()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo do disco", e);
        }
    }

    @Override
    public void deletarPorId(Integer id) {
        Documento doc = buscarPorId(id);
        try {
            Files.deleteIfExists(Paths.get(doc.getNomeGerado()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo do disco", e);
        }
        documentoRepository.delete(doc);
    }
}
