package clube_tamoios.service;

import clube_tamoios.dto.response.UnidadeDetalheResponse;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Especialidade;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Turma;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ClasseRepository;
import clube_tamoios.repository.EspecialidadeRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final PessoaRepository pessoaRepository;
    private final TurmaRepository turmaRepository;

    public ClasseService(ClasseRepository classeRepository,
                         EspecialidadeRepository especialidadeRepository,
                         PessoaRepository pessoaRepository,
                         TurmaRepository turmaRepository) {
        this.classeRepository = classeRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.pessoaRepository = pessoaRepository;
        this.turmaRepository = turmaRepository;
    }

    public List<Classe> listar() {
        return classeRepository.findAll();
    }

    public Classe buscarPorId(Integer idClasse) {
        return classeRepository.findById(idClasse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Classe não encontrada: " + idClasse));
    }

    /* ------------------------------------------------------------ participantes */

    // Só quem está ativo aparece na tela da classe: desbravador desativado
    // continua com o vínculo no banco, mas não conta como participante.
    public List<Pessoa> listarParticipantes(Integer idClasse) {
        buscarPorId(idClasse);

        return pessoaRepository.findByClasseIdClasseAndAtivoTrue(idClasse);
    }

    /* ----------------------------------------------------------- especialidades */

    @Transactional(readOnly = true)
    public List<Especialidade> listarEspecialidades(Integer idClasse) {
        Classe classe = buscarPorId(idClasse);
        // Força a carga dentro da transação: a coleção é LAZY.
        return new ArrayList<>(classe.getEspecialidades());
    }

    @Transactional
    public List<Especialidade> vincularEspecialidades(Integer idClasse, List<Integer> ids) {
        Classe classe = buscarPorId(idClasse);

        for (Integer idEspecialidade : ids) {
            Especialidade especialidade = especialidadeRepository.findById(idEspecialidade)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Especialidade não encontrada: " + idEspecialidade));

            // A tabela Disciplina tem chave primária composta: inserir o mesmo par
            // duas vezes estoura violação de PK, então o vínculo repetido é ignorado.
            boolean jaVinculada = classe.getEspecialidades().stream()
                    .anyMatch(item -> item.getIdEspecialidade().equals(idEspecialidade));

            if (!jaVinculada) {
                classe.getEspecialidades().add(especialidade);
            }
        }

        classeRepository.save(classe);
        return new ArrayList<>(classe.getEspecialidades());
    }

    @Transactional
    public void desvincularEspecialidade(Integer idClasse, Integer idEspecialidade) {
        Classe classe = buscarPorId(idClasse);

        boolean removida = classe.getEspecialidades()
                .removeIf(item -> item.getIdEspecialidade().equals(idEspecialidade));

        if (!removida) {
            throw new EntidadeNaoEncontradaException(
                    "Especialidade " + idEspecialidade + " não está vinculada à classe " + idClasse);
        }

        classeRepository.save(classe);
    }

    /* ---------------------------------------------------------------- unidades */

    public List<UnidadeDetalheResponse> listarUnidades(Integer idClasse) {
        buscarPorId(idClasse);

        return turmaRepository.findByClasseIdClasse(idClasse).stream()
                .map(Turma::getUnidade)
                .filter(unidade -> unidade != null)
                .map(unidade -> {
                    UnidadeDetalheResponse dto = new UnidadeDetalheResponse();
                    dto.setId(unidade.getIdUnidade());
                    dto.setNome(unidade.getNome());
                    dto.setFaixaEtaria(unidade.getFaixaEtaria());

                    if (unidade.getGenero() != null) {
                        dto.setIdGenero(unidade.getGenero().getIdGenero());
                        dto.setNomeGenero(unidade.getGenero().getNome());
                    }

                    if (unidade.getConselheiro() != null) {
                        dto.setIdConselheiro(unidade.getConselheiro().getIdPessoa());
                        dto.setNomeConselheiro(unidade.getConselheiro().getNome());
                    }

                    // Quantidade de desbravadores da unidade dentro desta classe —
                    // a mesma unidade pode atender mais de uma classe.
                    dto.setQuantidadeDesbravadores(
                            pessoaRepository.countByUnidadeIdUnidadeAndClasseIdClasseAndAtivoTrue(
                                    unidade.getIdUnidade(), idClasse));
                    return dto;
                })
                .toList();
    }
}
