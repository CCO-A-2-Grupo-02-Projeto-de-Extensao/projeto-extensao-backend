package clube_tamoios.service;

import clube_tamoios.dto.request.HistoricoEscolarAtualizacaoRequest;
import clube_tamoios.dto.request.HistoricoEscolarCadastroRequest;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.HistoricoEscolar;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.HistoricoEscolarRepository;
import clube_tamoios.repository.PessoaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HistoricoEscolarService {

    private final HistoricoEscolarRepository repository;
    private final PessoaRepository pessoaRepository;
    private final DocumentoRepository documentoRepository;

    public HistoricoEscolarService(HistoricoEscolarRepository repository,
            PessoaRepository pessoaRepository,
            DocumentoRepository documentoRepository) {
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
        this.documentoRepository = documentoRepository;
    }

    public HistoricoEscolar cadastrar(HistoricoEscolarCadastroRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));

        if (repository.existsByPessoaIdPessoaAndAno(request.getIdPessoa(), request.getAno())) {
            throw new RegraDeNegocioException("Já existe histórico escolar de " + request.getAno() + " para esta pessoa.");
        }

        HistoricoEscolar historico = new HistoricoEscolar();
        historico.setPessoa(pessoa);
        historico.setAno(request.getAno());
        preencher(historico, request);
        return repository.save(historico);
    }

    public List<HistoricoEscolar> listarPorPessoa(Integer idPessoa) {
        return repository.findByPessoaIdPessoaOrderByAnoDesc(idPessoa);
    }

    public HistoricoEscolar buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Histórico escolar não encontrado: " + id));
    }

    public HistoricoEscolar atualizar(Integer id, HistoricoEscolarAtualizacaoRequest request) {
        HistoricoEscolar historico = buscarPorId(id);
        preencher(historico, request);
        return repository.save(historico);
    }

    public void deletar(Integer id) {
        repository.delete(buscarPorId(id));
    }

    private void preencher(HistoricoEscolar historico, HistoricoEscolarAtualizacaoRequest request) {
        historico.setSerie(request.getSerie());
        historico.setObediencia(request.isObediencia());
        historico.setPrestatividade(request.isPrestatividade());
        historico.setParticipacao(request.isParticipacao());
        historico.setBoletim(buscarDocumento(request.getIdBoletim()));
        historico.setDeclaracao(buscarDocumento(request.getIdDeclaracao()));
        historico.setOutroClube(request.isOutroClube());
        historico.setClubeOrigem(request.getClubeOrigem());
        historico.setClasseConcluidaOrigem(request.isClasseConcluidaOrigem());
    }

    private Documento buscarDocumento(Integer id) {
        if (id == null) {
            return null;
        }
        return documentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + id));
    }
}
