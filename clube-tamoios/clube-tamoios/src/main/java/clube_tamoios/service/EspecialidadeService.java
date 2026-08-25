package clube_tamoios.service;

import clube_tamoios.dto.request.EspecialidadeRequest;
import clube_tamoios.entity.Especialidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public List<Especialidade> listar() {
        return repository.findAll();
    }

    public Especialidade buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Especialidade não encontrada: " + id));
    }

    public Especialidade cadastrar(EspecialidadeRequest request) {
        Especialidade especialidade = new Especialidade();
        aplicar(especialidade, request);
        return repository.save(especialidade);
    }

    public Especialidade atualizar(Integer id, EspecialidadeRequest request) {
        Especialidade especialidade = buscarPorId(id);
        aplicar(especialidade, request);
        return repository.save(especialidade);
    }

    // Apagar a especialidade limpa os vínculos com as classes junto: a FK da
    // tabela Disciplina é ON DELETE CASCADE.
    public void deletar(Integer id) {
        repository.delete(buscarPorId(id));
    }

    private void aplicar(Especialidade especialidade, EspecialidadeRequest request) {
        especialidade.setNome(request.getNome().trim());
        especialidade.setCategoria(request.getCategoria());
        especialidade.setDescricao(request.getDescricao());
        especialidade.setImagem(request.getImagem());
    }
}
