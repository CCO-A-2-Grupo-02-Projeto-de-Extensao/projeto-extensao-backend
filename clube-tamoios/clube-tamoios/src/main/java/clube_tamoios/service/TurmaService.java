package clube_tamoios.service;

import clube_tamoios.dto.request.TurmaRequest;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Turma;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ClasseRepository;
import clube_tamoios.repository.TurmaRepository;
import clube_tamoios.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final ClasseRepository classeRepository;
    private final UnidadeRepository unidadeRepository;

    public TurmaService(TurmaRepository turmaRepository,
                        ClasseRepository classeRepository,
                        UnidadeRepository unidadeRepository) {
        this.turmaRepository = turmaRepository;
        this.classeRepository = classeRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public Turma cadastrar(TurmaRequest request) {
        Classe classe = classeRepository.findById(request.getIdClasse())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Classe não encontrada: " + request.getIdClasse()));

        Unidade unidade = unidadeRepository.findById(request.getIdUnidade())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade não encontrada: " + request.getIdUnidade()));

        Turma turma = new Turma();
        turma.setClasse(classe);
        turma.setUnidade(unidade);

        return turmaRepository.save(turma);
    }

    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    public Turma buscarPorId(Integer id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Turma não encontrada: " + id));
    }

    public List<Turma> listarPorClasse(Integer idClasse) {
        return turmaRepository.findByClasseIdClasse(idClasse);
    }

    public List<Turma> listarPorUnidade(Integer idUnidade) {
        return turmaRepository.findByUnidadeIdUnidade(idUnidade);
    }

    public Turma atualizar(Integer id, TurmaRequest request) {
        Turma turma = buscarPorId(id);

        Classe classe = classeRepository.findById(request.getIdClasse())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Classe não encontrada: " + request.getIdClasse()));

        Unidade unidade = unidadeRepository.findById(request.getIdUnidade())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade não encontrada: " + request.getIdUnidade()));

        turma.setClasse(classe);
        turma.setUnidade(unidade);

        return turmaRepository.save(turma);
    }

    public void deletar(Integer id) {
        Turma turma = buscarPorId(id);
        turmaRepository.delete(turma);
    }
}