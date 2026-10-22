package clube_tamoios.service;

import clube_tamoios.dto.request.EspecialidadeRequest;
import clube_tamoios.entity.Especialidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.EspecialidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Especialidade> listarPaginado(String busca, String categoria, Pageable pageable) {
        Specification<Especialidade> filtro = Specification.where(null);

        if (busca != null && !busca.isBlank()) {
            String termo = "%" + busca.trim().toLowerCase() + "%";
            filtro = filtro.and((root, query, cb) -> cb.like(cb.lower(root.get("nome")), termo));
        }
        if (categoria != null && !categoria.isBlank()) {
            filtro = filtro.and((root, query, cb) -> cb.equal(root.get("categoria"), categoria));
        }

        return repository.findAll(filtro, pageable);
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
