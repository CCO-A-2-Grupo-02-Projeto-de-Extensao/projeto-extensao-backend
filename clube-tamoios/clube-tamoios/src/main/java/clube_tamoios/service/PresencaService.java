package clube_tamoios.service;

import clube_tamoios.dto.request.PresencaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Presenca;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.PresencaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final ChamadaRepository chamadaRepository;
    private final PessoaRepository pessoaRepository;

    public PresencaService(PresencaRepository presencaRepository,
                           ChamadaRepository chamadaRepository,
                           PessoaRepository pessoaRepository) {
        this.presencaRepository = presencaRepository;
        this.chamadaRepository = chamadaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Presenca registrar(PresencaRequest request) {
        Chamada chamada = chamadaRepository.findById(request.getIdChamada())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Chamada não encontrada: " + request.getIdChamada()));

        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada: " + request.getIdPessoa()));

        Presenca presenca = new Presenca();
        presenca.setChamada(chamada);
        presenca.setPessoa(pessoa);
        presenca.setPresente(request.getPresente());

        return presencaRepository.save(presenca);
    }

    public List<Presenca> listarPorChamada(Integer idChamada) {
        return presencaRepository.findByChamadaIdChamada(idChamada);
    }

    public Presenca buscarPorId(Integer id) {
        return presencaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Presença não encontrada: " + id));
    }

    public Presenca atualizar(Integer id, PresencaRequest request) {
        Presenca presenca = buscarPorId(id);

        presenca.setPresente(request.getPresente());
        // Se a regra de negócio permitir mudar a pessoa ou chamada da presença existente, você atualiza aqui.
        // Geralmente apenas altera-se o status booleano (presente/ausente).

        return presencaRepository.save(presenca);
    }

    public void deletar(Integer id) {
        Presenca presenca = buscarPorId(id);
        presencaRepository.delete(presenca);
    }
}