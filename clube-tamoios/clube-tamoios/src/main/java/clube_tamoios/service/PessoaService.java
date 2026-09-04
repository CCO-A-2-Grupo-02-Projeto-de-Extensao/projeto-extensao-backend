package clube_tamoios.service;

import clube_tamoios.dto.request.PessoaCadastroRequest;
import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Genero;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.CargoRepository;
import clube_tamoios.repository.ClasseRepository;
import clube_tamoios.repository.GeneroRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.UnidadeRepository;
import clube_tamoios.repository.UsuarioRepository;
import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaService {

    private static final Map<Integer, String> CLASSE_POR_IDADE = Map.of(
            10, "amigo",
            11, "companheiro",
            12, "pesquisador",
            13, "pioneiro",
            14, "excursionista",
            15, "guia");

    private static final String CARGO_ALUNO = "desbravador";

    private final PessoaRepository pessoaRepository;
    private final ClasseRepository classeRepository;
    private final GeneroRepository generoRepository;
    private final UnidadeRepository unidadeRepository;
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;

    public PessoaService(PessoaRepository pessoaRepository,
            ClasseRepository classeRepository,
            GeneroRepository generoRepository,
            UnidadeRepository unidadeRepository,
            CargoRepository cargoRepository,
            UsuarioRepository usuarioRepository) {
        this.pessoaRepository = pessoaRepository;
        this.classeRepository = classeRepository;
        this.generoRepository = generoRepository;
        this.unidadeRepository = unidadeRepository;
        this.cargoRepository = cargoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private void aplicarCampos(Pessoa pessoa, PessoaCadastroRequest request) {
        pessoa.setNome(request.getNome());
        pessoa.setCpf(request.getCpf());
        pessoa.setRg(request.getRg());
        pessoa.setDataNascimento(request.getDataNascimento());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setDesbravador(request.getIsDesbravador());
        pessoa.setEscola(request.getEscola());
        pessoa.setSerieEscolar(request.getSerieEscolar());
        pessoa.setNomeResponsavel1(request.getNomeResponsavel1());
        pessoa.setTelefoneResponsavel1(request.getTelefoneResponsavel1());
        pessoa.setRgResponsavel1(request.getRgResponsavel1());
        pessoa.setCpfResponsavel1(request.getCpfResponsavel1());
        pessoa.setNomeResponsavel2(request.getNomeResponsavel2());
        pessoa.setTelefoneResponsavel2(request.getTelefoneResponsavel2());
        pessoa.setRgResponsavel2(request.getRgResponsavel2());
        pessoa.setCpfResponsavel2(request.getCpfResponsavel2());

        if (request.getIdClasse() != null) {
            Classe classe = classeRepository.findById(request.getIdClasse())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Classe não encontrada: " + request.getIdClasse()));
            pessoa.setClasse(classe);
        } else {
            pessoa.setClasse(null);
        }

        if (request.getIdGenero() != null) {
            Genero genero = generoRepository.findById(request.getIdGenero())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Gênero não encontrado: " + request.getIdGenero()));
            pessoa.setGenero(genero);
        } else {
            pessoa.setGenero(null);
        }

        if (request.getIdUnidade() != null) {
            Unidade unidade = unidadeRepository.findById(request.getIdUnidade())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade não encontrada: " + request.getIdUnidade()));
            validarEntradaNaUnidade(pessoa, unidade);
            pessoa.setUnidade(unidade);
        } else {
            pessoa.setUnidade(null);
        }

        if (request.getIdCargo() != null) {
            Cargo cargo = cargoRepository.findById(request.getIdCargo())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado: " + request.getIdCargo()));
            pessoa.setCargo(cargo);
        } else {
            pessoa.setCargo(null);
        }
    }

    public Pessoa cadastrar(PessoaCadastroRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setAtivo(true);
        aplicarCampos(pessoa, request);

        if (pessoa.getClasse() == null && !Boolean.FALSE.equals(request.getIsDesbravador())) {
            classeDaIdade(request.getDataNascimento()).ifPresent(classe -> {
                pessoa.setClasse(classe);
                if (pessoa.getCargo() == null) {
                    cargoAluno().ifPresent(pessoa::setCargo);
                }
            });
        }

        return pessoaRepository.save(pessoa);
    }

    private static String normalizar(String valor) {
        return Normalizer.normalize(valor == null ? "" : valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .trim()
                .toLowerCase();
    }

    private Optional<Classe> classeDaIdade(String dataNascimento) {
        Integer idade = RegrasUnidade.idadeEm(dataNascimento);
        if (idade == null) {
            return Optional.empty();
        }

        String nomeDaClasse = CLASSE_POR_IDADE.get(idade);
        if (nomeDaClasse == null) {
            return Optional.empty();
        }

        return classeRepository.findAll().stream()
                .filter(classe -> normalizar(classe.getNome()).equals(nomeDaClasse))
                .findFirst();
    }

    private Optional<Cargo> cargoAluno() {
        return cargoRepository.findAll().stream()
                .filter(cargo -> normalizar(cargo.getNome()).equals(CARGO_ALUNO))
                .findFirst();
    }

    private void validarEntradaNaUnidade(Pessoa pessoa, Unidade unidade) {
        if (pessoa.getUnidade() != null
                && pessoa.getUnidade().getIdUnidade().equals(unidade.getIdUnidade())) {
            return;
        }

        long ocupacao = pessoaRepository.findAll().stream()
                .filter(outra -> Boolean.TRUE.equals(outra.getAtivo()))
                .filter(RegrasUnidade::contaComoDesbravador)
                .filter(outra -> outra.getUnidade() != null
                        && outra.getUnidade().getIdUnidade().equals(unidade.getIdUnidade()))
                .filter(outra -> !outra.getIdPessoa().equals(pessoa.getIdPessoa()))
                .count();

        RegrasUnidade.validarEntrada(pessoa, unidade, ocupacao);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPorId(Integer id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + id));
    }

    public Pessoa atualizar(Integer id, PessoaCadastroRequest request) {
        Pessoa pessoa = buscarPorId(id);
        aplicarCampos(pessoa, request);
        return pessoaRepository.save(pessoa);
    }

    // Desativar/reativar a Pessoa também alterna o Usuario vinculado (se
    // existir), já que perder o cadastro ativo deve derrubar o login também.
    // Vincular/desvincular sem passar pelo PessoaCadastroRequest inteiro: a tela
    // de classe só mexe nesse campo, e exigir o formulário completo faria a
    // remoção depender de dados que a tela não tem em mãos.
    public Pessoa definirClasse(Integer idPessoa, Integer idClasse) {
        Pessoa pessoa = buscarPorId(idPessoa);

        if (idClasse == null) {
            pessoa.setClasse(null);
        } else {
            pessoa.setClasse(classeRepository.findById(idClasse)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Classe não encontrada: " + idClasse)));
        }

        return pessoaRepository.save(pessoa);
    }

    public Pessoa definirUnidade(Integer idPessoa, Integer idUnidade) {
        Pessoa pessoa = buscarPorId(idPessoa);

        if (idUnidade == null) {
            pessoa.setUnidade(null);
        } else {
            Unidade unidade = unidadeRepository.findById(idUnidade)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Unidade não encontrada: " + idUnidade));
            validarEntradaNaUnidade(pessoa, unidade);
            pessoa.setUnidade(unidade);
        }

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void desativar(Integer id) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);

        usuarioRepository.findByPessoaIdPessoa(id).ifPresent(usuario -> {
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public void reativar(Integer id) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.setAtivo(true);
        pessoaRepository.save(pessoa);

        usuarioRepository.findByPessoaIdPessoa(id).ifPresent(usuario -> {
            usuario.setAtivo(true);
            usuarioRepository.save(usuario);
        });
    }
}
