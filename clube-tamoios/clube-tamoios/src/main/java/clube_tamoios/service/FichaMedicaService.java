package clube_tamoios.service;

import clube_tamoios.dto.request.FichaMedicaCadastroRequest;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.FichaMedicaRepository;
import clube_tamoios.repository.PessoaRepository;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class FichaMedicaService {

    private final FichaMedicaRepository fichaMedicaRepository;
    private final PessoaRepository pessoaRepository;

    public FichaMedicaService(FichaMedicaRepository fichaMedicaRepository,
            PessoaRepository pessoaRepository) {
        this.fichaMedicaRepository = fichaMedicaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public FichaMedica criar(FichaMedicaCadastroRequest request) {
        if (fichaMedicaRepository.existsByPessoaIdPessoa(request.getIdPessoa())) {
            throw new RegraDeNegocioException("Pessoa já possui ficha médica cadastrada.");
        }
        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));

        FichaMedica ficha = new FichaMedica();
        ficha.setPessoa(pessoa);
        ficha.setMedicacoes(new ArrayList<>());
        ficha.setDiagnosticos(new ArrayList<>());
        return fichaMedicaRepository.save(ficha);
    }

    public FichaMedica buscarPorId(Integer id) {
        return fichaMedicaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ficha médica não encontrada: " + id));
    }

    public FichaMedica buscarPorPessoa(Integer idPessoa) {
        return fichaMedicaRepository.findByPessoaIdPessoa(idPessoa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ficha médica não encontrada para a pessoa: " + idPessoa));
    }

    public void deletar(Integer id) {
        FichaMedica ficha = buscarPorId(id);
        fichaMedicaRepository.delete(ficha);
    }
}
