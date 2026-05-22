package clube_tamoios.service;

import clube_tamoios.dto.request.DiagnosticoAtualizacaoRequest;
import clube_tamoios.dto.request.DiagnosticoCadastroRequest;
import clube_tamoios.entity.Comorbidade;
import clube_tamoios.entity.Diagnostico;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ComorbidadeRepository;
import clube_tamoios.repository.DiagnosticoRepository;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.FichaMedicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiagnosticoServiceTest {

    @Mock
    private DiagnosticoRepository diagnosticoRepository;

    @Mock
    private FichaMedicaRepository fichaMedicaRepository;

    @Mock
    private ComorbidadeRepository comorbidadeRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @InjectMocks
    private DiagnosticoService service;

    private FichaMedica ficha;
    private Comorbidade comorbidade;
    private Documento documento;
    private Diagnostico diagnostico;
    private DiagnosticoCadastroRequest cadastroRequest;

    @BeforeEach
    void setUp() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        ficha = new FichaMedica();
        ficha.setId(1);
        ficha.setPessoa(pessoa);

        comorbidade = new Comorbidade();
        comorbidade.setId(1);
        comorbidade.setNome("Diabetes");

        documento = new Documento();
        documento.setId(1);
        documento.setNomeOriginal("laudo.pdf");

        diagnostico = new Diagnostico();
        diagnostico.setId(1);
        diagnostico.setFichaMedica(ficha);
        diagnostico.setComorbidade(comorbidade);

        cadastroRequest = new DiagnosticoCadastroRequest();
        cadastroRequest.setIdFichaMedica(1);
        cadastroRequest.setIdComorbidade(1);
    }

    @Test
    void cadastrar_deveRetornarDiagnosticoSalvo_quandoDadosValidos() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(comorbidadeRepository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        Diagnostico resultado = service.cadastrar(cadastroRequest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getComorbidade().getNome()).isEqualTo("Diabetes");
        verify(diagnosticoRepository).save(any(Diagnostico.class));
    }

    @Test
    void cadastrar_deveAssociarDocumento_quandoIdDocumentoInformado() {
        cadastroRequest.setIdDocumento(1);

        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(comorbidadeRepository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        Diagnostico resultado = service.cadastrar(cadastroRequest);

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
    void cadastrar_deveLancarExcecao_quandoComorbidadeNaoEncontrada() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));
        when(comorbidadeRepository.findById(99)).thenReturn(Optional.empty());
        cadastroRequest.setIdComorbidade(99);

        assertThatThrownBy(() -> service.cadastrar(cadastroRequest))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listarPorFicha_deveRetornarDiagnosticosDaFicha() {
        when(diagnosticoRepository.findByFichaMedicaId(1)).thenReturn(List.of(diagnostico));

        List<Diagnostico> resultado = service.listarPorFicha(1);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1);
    }

    @Test
    void listarPorFicha_deveRetornarListaVazia_quandoNaoHaDiagnosticos() {
        when(diagnosticoRepository.findByFichaMedicaId(99)).thenReturn(List.of());

        List<Diagnostico> resultado = service.listarPorFicha(99);

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarDiagnostico_quandoExistente() {
        when(diagnosticoRepository.findById(1)).thenReturn(Optional.of(diagnostico));

        Diagnostico resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(diagnosticoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveAtualizarComorbidade_quandoDadosValidos() {
        DiagnosticoAtualizacaoRequest atualizacaoRequest = new DiagnosticoAtualizacaoRequest();
        atualizacaoRequest.setIdComorbidade(1);

        when(diagnosticoRepository.findById(1)).thenReturn(Optional.of(diagnostico));
        when(comorbidadeRepository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        Diagnostico resultado = service.atualizar(1, atualizacaoRequest);

        assertThat(resultado).isNotNull();
        verify(diagnosticoRepository).save(diagnostico);
    }

    @Test
    void atualizar_deveAssociarDocumento_quandoIdDocumentoInformado() {
        DiagnosticoAtualizacaoRequest atualizacaoRequest = new DiagnosticoAtualizacaoRequest();
        atualizacaoRequest.setIdComorbidade(1);
        atualizacaoRequest.setIdDocumento(1);

        when(diagnosticoRepository.findById(1)).thenReturn(Optional.of(diagnostico));
        when(comorbidadeRepository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        service.atualizar(1, atualizacaoRequest);

        verify(documentoRepository).findById(1);
    }

    @Test
    void atualizar_deveLimparDocumento_quandoIdDocumentoNulo() {
        DiagnosticoAtualizacaoRequest atualizacaoRequest = new DiagnosticoAtualizacaoRequest();
        atualizacaoRequest.setIdComorbidade(1);
        diagnostico.setDocumento(documento);

        when(diagnosticoRepository.findById(1)).thenReturn(Optional.of(diagnostico));
        when(comorbidadeRepository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(diagnosticoRepository.save(any(Diagnostico.class))).thenReturn(diagnostico);

        service.atualizar(1, atualizacaoRequest);

        assertThat(diagnostico.getDocumento()).isNull();
    }

    @Test
    void atualizar_deveLancarExcecao_quandoNaoEncontrado() {
        DiagnosticoAtualizacaoRequest atualizacaoRequest = new DiagnosticoAtualizacaoRequest();
        atualizacaoRequest.setIdComorbidade(1);

        when(diagnosticoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, atualizacaoRequest))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(diagnosticoRepository.findById(1)).thenReturn(Optional.of(diagnostico));

        service.deletar(1);

        verify(diagnosticoRepository).delete(diagnostico);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrado() {
        when(diagnosticoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
