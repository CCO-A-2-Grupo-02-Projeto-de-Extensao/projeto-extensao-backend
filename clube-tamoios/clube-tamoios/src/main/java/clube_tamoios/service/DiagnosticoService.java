package clube_tamoios.service;

import clube_tamoios.dto.request.DiagnosticoAtualizacaoRequest;
import clube_tamoios.dto.request.DiagnosticoCadastroRequest;
import clube_tamoios.entity.Comorbidade;
import clube_tamoios.entity.Diagnostico;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ComorbidadeRepository;
import clube_tamoios.repository.DiagnosticoRepository;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.FichaMedicaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final FichaMedicaRepository fichaMedicaRepository;
    private final ComorbidadeRepository comorbidadeRepository;
    private final DocumentoRepository documentoRepository;

    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository,
            FichaMedicaRepository fichaMedicaRepository,
            ComorbidadeRepository comorbidadeRepository,
            DocumentoRepository documentoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.fichaMedicaRepository = fichaMedicaRepository;
        this.comorbidadeRepository = comorbidadeRepository;
        this.documentoRepository = documentoRepository;
    }

    public Diagnostico cadastrar(DiagnosticoCadastroRequest request) {
        FichaMedica ficha = fichaMedicaRepository.findById(request.getIdFichaMedica())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ficha médica não encontrada: " + request.getIdFichaMedica()));

        Comorbidade comorbidade = comorbidadeRepository.findById(request.getIdComorbidade())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Comorbidade não encontrada: " + request.getIdComorbidade()));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setFichaMedica(ficha);
        diagnostico.setComorbidade(comorbidade);

        if (request.getIdDocumento() != null) {
            Documento doc = documentoRepository.findById(request.getIdDocumento())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + request.getIdDocumento()));
            diagnostico.setDocumento(doc);
        }

        return diagnosticoRepository.save(diagnostico);
    }

    public List<Diagnostico> listarPorFicha(Integer idFichaMedica) {
        return diagnosticoRepository.findByFichaMedicaId(idFichaMedica);
    }

    public Diagnostico buscarPorId(Integer id) {
        return diagnosticoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Diagnóstico não encontrado: " + id));
    }

    public Diagnostico atualizar(Integer id, DiagnosticoAtualizacaoRequest request) {
        Diagnostico diagnostico = buscarPorId(id);

        Comorbidade comorbidade = comorbidadeRepository.findById(request.getIdComorbidade())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Comorbidade não encontrada: " + request.getIdComorbidade()));

        diagnostico.setComorbidade(comorbidade);

        if (request.getIdDocumento() != null) {
            Documento doc = documentoRepository.findById(request.getIdDocumento())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento não encontrado: " + request.getIdDocumento()));
            diagnostico.setDocumento(doc);
        } else {
            diagnostico.setDocumento(null);
        }

        return diagnosticoRepository.save(diagnostico);
    }

    public void deletar(Integer id) {
        Diagnostico diagnostico = buscarPorId(id);
        diagnosticoRepository.delete(diagnostico);
    }
}
