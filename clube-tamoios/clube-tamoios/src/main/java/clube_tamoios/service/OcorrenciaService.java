package clube_tamoios.service;

import clube_tamoios.dto.request.OcorrenciaCadastroRequest;
import clube_tamoios.entity.Ocorrencia;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.OcorrenciaRepository;
import clube_tamoios.repository.PessoaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final PessoaRepository pessoaRepository;

    public OcorrenciaService(OcorrenciaRepository repository, PessoaRepository pessoaRepository) {
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
    }

    public Ocorrencia cadastrar(OcorrenciaCadastroRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setData(request.getData());
        ocorrencia.setDescricao(request.getDescricao());
        ocorrencia.setPessoa(pessoa);
        return repository.save(ocorrencia);
    }

    public List<Ocorrencia> listar() {
        return repository.findAll();
    }

    public List<Ocorrencia> listarPorPessoa(Integer idPessoa) {
        return repository.findByPessoaIdPessoa(idPessoa);
    }

    public Ocorrencia buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ocorrência não encontrada: " + id));
    }

    public Ocorrencia atualizar(Integer id, OcorrenciaCadastroRequest request) {
        Ocorrencia ocorrencia = buscarPorId(id);

        if (request.getIdPessoa() != null && !request.getIdPessoa().equals(ocorrencia.getPessoa().getIdPessoa())) {
            Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));
            ocorrencia.setPessoa(pessoa);
        }

        ocorrencia.setData(request.getData());
        ocorrencia.setDescricao(request.getDescricao());
        return repository.save(ocorrencia);
    }

    public void deletar(Integer id) {
        Ocorrencia ocorrencia = buscarPorId(id);
        repository.delete(ocorrencia);
    }
}
