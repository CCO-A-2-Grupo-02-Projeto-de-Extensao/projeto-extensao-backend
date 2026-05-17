package clube_tamoios.service;

import clube_tamoios.dto.request.MedicacaoAtualizacaoRequest;
import clube_tamoios.dto.request.MedicacaoCadastroRequest;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.entity.Medicacao;
import clube_tamoios.entity.Medicamento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.FichaMedicaRepository;
import clube_tamoios.repository.MedicacaoRepository;
import clube_tamoios.repository.MedicamentoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MedicacaoService {

    private final MedicacaoRepository medicacaoRepository;
    private final FichaMedicaRepository fichaMedicaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final DocumentoRepository documentoRepository;

    public MedicacaoService(MedicacaoRepository medicacaoRepository,
            FichaMedicaRepository fichaMedicaRepository,
            MedicamentoRepository medicamentoRepository,
            DocumentoRepository documentoRepository) {
        this.medicacaoRepository = medicacaoRepository;
        this.fichaMedicaRepository = fichaMedicaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.documentoRepository = documentoRepository;
    }

    public Medicacao cadastrar(MedicacaoCadastroRequest request) {
        FichaMedica ficha = fichaMedicaRepository.findById(request.getIdFichaMedica())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ficha médica não encontrada: " + request.getIdFichaMedica()));

        Medicamento medicamento = medicamentoRepository.findById(request.getIdMedicamento())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Medicamento não encontrado: " + request.getIdMedicamento()));

        Medicacao medicacao = new Medicacao();
        medicacao.setFichaMedica(ficha);
        medicacao.setMedicamento(medicamento);
        medicacao.setHorarioInicio(request.getHorarioInicio());
        medicacao.setHorarioFim(request.getHorarioFim());
        medicacao.setDose(request.getDose());

        if (request.getIdDocumento() != null) {
            Documento doc = documentoRepository.findById(request.getIdDocumento())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + request.getIdDocumento()));
            medicacao.setDocumento(doc);
        }

        return medicacaoRepository.save(medicacao);
    }

    public List<Medicacao> listarPorFicha(Integer idFichaMedica) {
        return medicacaoRepository.findByFichaMedicaId(idFichaMedica);
    }

    public Medicacao buscarPorId(Integer id) {
        return medicacaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Medicação não encontrada: " + id));
    }

    public Medicacao atualizar(Integer id, MedicacaoAtualizacaoRequest request) {
        Medicacao medicacao = buscarPorId(id);

        Medicamento medicamento = medicamentoRepository.findById(request.getIdMedicamento())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Medicamento não encontrado: " + request.getIdMedicamento()));

        medicacao.setMedicamento(medicamento);
        medicacao.setHorarioInicio(request.getHorarioInicio());
        medicacao.setHorarioFim(request.getHorarioFim());
        medicacao.setDose(request.getDose());

        if (request.getIdDocumento() != null) {
            Documento doc = documentoRepository.findById(request.getIdDocumento())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + request.getIdDocumento()));
            medicacao.setDocumento(doc);
        } else {
            medicacao.setDocumento(null);
        }

        return medicacaoRepository.save(medicacao);
    }

    public void deletar(Integer id) {
        Medicacao medicacao = buscarPorId(id);
        medicacaoRepository.delete(medicacao);
    }
}
