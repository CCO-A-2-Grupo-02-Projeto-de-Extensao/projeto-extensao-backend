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
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

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
        return pessoaRepository.save(pessoa);
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
    public void desativar(Integer id) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);

        usuarioRepository.findByPessoaIdPessoa(id).ifPresent(usuario -> {
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        });
    }

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
