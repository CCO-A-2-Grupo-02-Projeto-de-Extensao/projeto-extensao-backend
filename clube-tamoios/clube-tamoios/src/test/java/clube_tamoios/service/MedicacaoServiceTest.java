package clube_tamoios.service;

import clube_tamoios.dto.request.MedicacaoAtualizacaoRequest;
import clube_tamoios.dto.request.MedicacaoCadastroRequest;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.entity.Medicacao;
import clube_tamoios.entity.Medicamento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.FichaMedicaRepository;
import clube_tamoios.repository.MedicacaoRepository;
import clube_tamoios.repository.MedicamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicacaoServiceTest {

    @Mock
    private MedicacaoRepository medicacaoRepository;

    @Mock
    private FichaMedicaRepository fichaMedicaRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @InjectMocks
    private MedicacaoService service;

    private FichaMedica ficha;
    private Medicamento medicamento;
    private Documento documento;
    private Medicacao medicacao;
    private MedicacaoCadastroRequest cadastroRequest;

    @BeforeEach
    void setUp() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        ficha = new FichaMedica();
        ficha.setId(1);
        ficha.setPessoa(pessoa);

        medicamento = new Medicamento();
        medicamento.setId(1);
        medicamento.setNome("Ritalina");

        documento = new Documento();
        documento.setId(1);
        documento.setNomeOriginal("receita.pdf");

        medicacao = new Medicacao();
        medicacao.setId(1);
        medicacao.setFichaMedica(ficha);
        medicacao.setMedicamento(medicamento);
        medicacao.setDose(new BigDecimal("10.00"));
        medicacao.setHorarioInicio(LocalDateTime.of(2026, 5, 22, 8, 0));
        medicacao.setHorarioFim(LocalDateTime.of(2026, 5, 22, 20, 0));

        cadastroRequest = new MedicacaoCadastroRequest();
        cadastroRequest.setIdFichaMedica(1);
        cadastroRequest.setIdMedicamento(1);
        cadastroRequest.setDose(new BigDecimal("10.00"));
        cadastroRequest.setHorarioInicio(LocalDateTime.of(2026, 5, 22, 8, 0));
        cadastroRequest.setHorarioFim(LocalDateTime.of(2026, 5, 22, 20, 0));
    }

    @Test
    void cadastrar_deveRetornarMedicacaoSalva_quandoDadosValidos() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicacaoRepository.save(any(Medicacao.class))).thenReturn(medicacao);

        Medicacao resultado = service.cadastrar(cadastroRequest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getMedicamento().getNome()).isEqualTo("Ritalina");
        verify(medicacaoRepository).save(any(Medicacao.class));
    }

    @Test
    void cadastrar_deveAssociarDocumento_quandoIdDocumentoInformado() {
        cadastroRequest.setIdDocumento(1);

        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));
        when(medicacaoRepository.save(any(Medicacao.class))).thenReturn(medicacao);

        Medicacao resultado = service.cadastrar(cadastroRequest);

        assertThat(resultado).isNotNull();
        verify(documentoRepository).findById(1);
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoFichaNaoEncontrada() {
        when(fichaMedicaRepository.findById(99)).thenReturn(Optional.empty());
        cadastroRequest.setIdFichaMedica(99);

        assertThatThrownBy(() -> service.cadastrar(cadastroRequest))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoMedicamentoNaoEncontrado() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(medicamentoRepository.findById(99)).thenReturn(Optional.empty());
        cadastroRequest.setIdMedicamento(99);

        assertThatThrownBy(() -> service.cadastrar(cadastroRequest))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listarPorFicha_deveRetornarMedicacoesDaFicha() {
        when(medicacaoRepository.findByFichaMedicaId(1)).thenReturn(List.of(medicacao));

        List<Medicacao> resultado = service.listarPorFicha(1);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1);
    }

    @Test
    void listarPorFicha_deveRetornarListaVazia_quandoNaoHaMedicacoes() {
        when(medicacaoRepository.findByFichaMedicaId(99)).thenReturn(List.of());

        List<Medicacao> resultado = service.listarPorFicha(99);

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarMedicacao_quandoExistente() {
        when(medicacaoRepository.findById(1)).thenReturn(Optional.of(medicacao));

        Medicacao resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrada() {
        when(medicacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveAtualizarCampos_quandoDadosValidos() {
        MedicacaoAtualizacaoRequest atualizacaoRequest = new MedicacaoAtualizacaoRequest();
        atualizacaoRequest.setIdMedicamento(1);
        atualizacaoRequest.setDose(new BigDecimal("20.00"));

        when(medicacaoRepository.findById(1)).thenReturn(Optional.of(medicacao));
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicacaoRepository.save(any(Medicacao.class))).thenReturn(medicacao);

        Medicacao resultado = service.atualizar(1, atualizacaoRequest);

        assertThat(resultado).isNotNull();
        verify(medicacaoRepository).save(medicacao);
    }

    @Test
    void atualizar_deveAssociarDocumento_quandoIdDocumentoInformado() {
        MedicacaoAtualizacaoRequest atualizacaoRequest = new MedicacaoAtualizacaoRequest();
        atualizacaoRequest.setIdMedicamento(1);
        atualizacaoRequest.setIdDocumento(1);

        when(medicacaoRepository.findById(1)).thenReturn(Optional.of(medicacao));
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));
        when(medicacaoRepository.save(any(Medicacao.class))).thenReturn(medicacao);

        service.atualizar(1, atualizacaoRequest);

        verify(documentoRepository).findById(1);
    }

    @Test
    void atualizar_deveLimparDocumento_quandoIdDocumentoNulo() {
        MedicacaoAtualizacaoRequest atualizacaoRequest = new MedicacaoAtualizacaoRequest();
        atualizacaoRequest.setIdMedicamento(1);
        medicacao.setDocumento(documento);

        when(medicacaoRepository.findById(1)).thenReturn(Optional.of(medicacao));
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicacaoRepository.save(any(Medicacao.class))).thenReturn(medicacao);

        service.atualizar(1, atualizacaoRequest);

        assertThat(medicacao.getDocumento()).isNull();
    }

    @Test
    void atualizar_deveLancarExcecao_quandoNaoEncontrada() {
        MedicacaoAtualizacaoRequest atualizacaoRequest = new MedicacaoAtualizacaoRequest();
        atualizacaoRequest.setIdMedicamento(1);

        when(medicacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, atualizacaoRequest))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(medicacaoRepository.findById(1)).thenReturn(Optional.of(medicacao));

        service.deletar(1);

        verify(medicacaoRepository).delete(medicacao);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrada() {
        when(medicacaoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
