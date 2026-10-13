package clube_tamoios.service;

import clube_tamoios.dto.request.EspecialidadeConcluidaRequest;
import clube_tamoios.entity.Especialidade;
import clube_tamoios.entity.EspecialidadeConcluida;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.EspecialidadeConcluidaRepository;
import clube_tamoios.repository.EspecialidadeRepository;
import clube_tamoios.repository.PessoaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadeConcluidaService {

    private final EspecialidadeConcluidaRepository repository;
    private final PessoaRepository pessoaRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeConcluidaService(EspecialidadeConcluidaRepository repository,
            PessoaRepository pessoaRepository,
            EspecialidadeRepository especialidadeRepository) {
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public EspecialidadeConcluida cadastrar(EspecialidadeConcluidaRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));
        Especialidade especialidade = especialidadeRepository.findById(request.getIdEspecialidade())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Especialidade não encontrada: " + request.getIdEspecialidade()));

        if (repository.existsByPessoaIdPessoaAndEspecialidadeIdEspecialidade(request.getIdPessoa(), request.getIdEspecialidade())) {
            throw new RegraDeNegocioException("Esta especialidade já foi concluída por esta pessoa.");
        }

        EspecialidadeConcluida concluida = new EspecialidadeConcluida();
        concluida.setPessoa(pessoa);
        concluida.setEspecialidade(especialidade);
        concluida.setAno(request.getAno());
        return repository.save(concluida);
    }

    public List<EspecialidadeConcluida> listarPorPessoa(Integer idPessoa) {
        return repository.findByPessoaIdPessoa(idPessoa);
    }

    public void deletar(Integer id) {
        EspecialidadeConcluida concluida = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Especialidade concluída não encontrada: " + id));
        repository.delete(concluida);
    }
}
