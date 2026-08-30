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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.storage.type", havingValue = "banco", matchIfMissing = true)
public class DocumentoBancoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PessoaRepository pessoaRepository;

    public DocumentoBancoServiceImpl(DocumentoRepository documentoRepository,
            PessoaRepository pessoaRepository) {
        this.documentoRepository = documentoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Documento salvar(ArquivoUpload arquivo, Integer idPessoa, String tipo) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + idPessoa));
        Documento doc = DocumentoMapper.toEntity(arquivo, pessoa, tipo);
        return documentoRepository.save(doc);
    }

    @Override
    public Documento substituir(Integer id, ArquivoUpload novoArquivo) {
        Documento doc = buscarPorId(id);
        doc.setNomeOriginal(novoArquivo.nomeOriginal());
        doc.setMimeType(novoArquivo.mimeType());
        doc.setTamanho(novoArquivo.tamanho());
        doc.setDados(novoArquivo.conteudo());
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
        return doc.getDados();
    }

    @Override
    public void deletarPorId(Integer id) {
        Documento doc = buscarPorId(id);
        documentoRepository.delete(doc);
    }
}
