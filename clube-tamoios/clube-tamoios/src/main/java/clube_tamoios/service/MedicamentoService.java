package clube_tamoios.service;

import clube_tamoios.dto.request.MedicamentoCadastroRequest;
import clube_tamoios.entity.Medicamento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.MedicamentoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {

    private final MedicamentoRepository repository;

    public MedicamentoService(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public Medicamento cadastrar(MedicamentoCadastroRequest request) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome(request.getNome());
        return repository.save(medicamento);
    }

    public List<Medicamento> listar() {
        return repository.findAll();
    }

    public Medicamento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Medicamento não encontrado: " + id));
    }

    public Medicamento atualizar(Integer id, MedicamentoCadastroRequest request) {
        Medicamento medicamento = buscarPorId(id);
        medicamento.setNome(request.getNome());
        return repository.save(medicamento);
    }

    public void deletar(Integer id) {
        Medicamento medicamento = buscarPorId(id);
        repository.delete(medicamento);
    }
}
