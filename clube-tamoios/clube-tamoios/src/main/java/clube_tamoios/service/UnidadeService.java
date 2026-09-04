package clube_tamoios.service;

import clube_tamoios.dto.request.UnidadeRequest;
import clube_tamoios.dto.response.UnidadeDetalheResponse;
import clube_tamoios.entity.Genero;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.GeneroRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.TurmaRepository;
import clube_tamoios.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final GeneroRepository generoRepository;
    private final PessoaRepository pessoaRepository;
    private final TurmaRepository turmaRepository;

    public UnidadeService(UnidadeRepository unidadeRepository,
                          GeneroRepository generoRepository,
                          PessoaRepository pessoaRepository,
                          TurmaRepository turmaRepository) {
        this.unidadeRepository = unidadeRepository;
        this.generoRepository = generoRepository;
        this.pessoaRepository = pessoaRepository;
        this.turmaRepository = turmaRepository;
    }

    public List<Unidade> listar() {
        return unidadeRepository.findAll();
    }

    public Unidade buscarPorId(Integer id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade não encontrada: " + id));
    }

    public List<UnidadeDetalheResponse> listarDetalhado() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return unidadeRepository.findAll().stream()
                .map(unidade -> toDetalhe(unidade, pessoas))
                .toList();
    }

    public Unidade cadastrar(UnidadeRequest request) {
        Unidade unidade = new Unidade();
        aplicar(unidade, request);
        return unidadeRepository.save(unidade);
    }

    public Unidade atualizar(Integer id, UnidadeRequest request) {
        Unidade unidade = buscarPorId(id);
        aplicar(unidade, request);
        return unidadeRepository.save(unidade);
    }

    // Apagar a unidade nunca apaga pessoa: quem estava nela fica sem unidade,
    // como no "remover da classe".
    @Transactional
    public void deletar(Integer id) {
        Unidade unidade = buscarPorId(id);

        List<Pessoa> daUnidade = pessoaRepository.findAll().stream()
                .filter(pessoa -> pessoa.getUnidade() != null
                        && pessoa.getUnidade().getIdUnidade().equals(id))
                .toList();

        daUnidade.forEach(pessoa -> pessoa.setUnidade(null));
        pessoaRepository.saveAll(daUnidade);

        turmaRepository.deleteAll(turmaRepository.findByUnidadeIdUnidade(id));
        unidadeRepository.delete(unidade);
    }

    public UnidadeDetalheResponse toDetalhe(Unidade unidade) {
        return toDetalhe(unidade, pessoaRepository.findAll());
    }

    private UnidadeDetalheResponse toDetalhe(Unidade unidade, List<Pessoa> pessoas) {
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

        List<Pessoa> desbravadores = pessoas.stream()
                .filter(pessoa -> Boolean.TRUE.equals(pessoa.getAtivo()))
                .filter(RegrasUnidade::contaComoDesbravador)
                .filter(pessoa -> pessoa.getUnidade() != null
                        && pessoa.getUnidade().getIdUnidade().equals(unidade.getIdUnidade()))
                .toList();

        dto.setQuantidadeDesbravadores((long) desbravadores.size());
        dto.setInconsistencias(RegrasUnidade.inconsistencias(unidade, desbravadores));
        return dto;
    }

    private void aplicar(Unidade unidade, UnidadeRequest request) {
        RegrasUnidade.validarDadosDaUnidade(request.getIdGenero(), request.getFaixaEtaria());

        unidade.setNome(request.getNome().trim());
        unidade.setFaixaEtaria(request.getFaixaEtaria());

        if (request.getIdGenero() == null) {
            unidade.setGenero(null);
        } else {
            Genero genero = generoRepository.findById(request.getIdGenero())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Gênero não encontrado: " + request.getIdGenero()));
            unidade.setGenero(genero);
        }

        if (request.getIdConselheiro() == null) {
            unidade.setConselheiro(null);
        } else {
            Pessoa conselheiro = pessoaRepository.findById(request.getIdConselheiro())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            "Pessoa não encontrada: " + request.getIdConselheiro()));
            unidade.setConselheiro(conselheiro);
        }
    }
}
