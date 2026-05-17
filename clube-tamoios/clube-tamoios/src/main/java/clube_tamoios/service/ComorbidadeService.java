package clube_tamoios.service;

import clube_tamoios.dto.request.ComorbidadeCadastroRequest;
import clube_tamoios.entity.Comorbidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ComorbidadeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ComorbidadeService {

    private final ComorbidadeRepository repository;

    public ComorbidadeService(ComorbidadeRepository repository) {
        this.repository = repository;
    }

    public Comorbidade cadastrar(ComorbidadeCadastroRequest request) {
        Comorbidade comorbidade = new Comorbidade();
        comorbidade.setNome(request.getNome());
        return repository.save(comorbidade);
    }

    public List<Comorbidade> listar() {
        return repository.findAll();
    }

    public Comorbidade buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Comorbidade não encontrada: " + id));
    }

    public Comorbidade atualizar(Integer id, ComorbidadeCadastroRequest request) {
        Comorbidade comorbidade = buscarPorId(id);
        comorbidade.setNome(request.getNome());
        return repository.save(comorbidade);
    }

    public void deletar(Integer id) {
        Comorbidade comorbidade = buscarPorId(id);
        repository.delete(comorbidade);
    }
}
