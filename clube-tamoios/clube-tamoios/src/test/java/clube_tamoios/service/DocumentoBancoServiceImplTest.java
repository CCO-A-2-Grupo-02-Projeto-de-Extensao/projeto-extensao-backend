package clube_tamoios.service;

import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.service.implementations.DocumentoBancoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentoBancoServiceImplTest {

    @Mock
    private DocumentoRepository documentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private DocumentoBancoServiceImpl service;

    private Pessoa pessoa;
    private Documento documento;
    private MockMultipartFile arquivo;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        documento = new Documento();
        documento.setId(1);
        documento.setPessoa(pessoa);
        documento.setNomeOriginal("laudo.pdf");
        documento.setMimeType("application/pdf");
        documento.setTamanho(1024L);
        documento.setDados("conteudo".getBytes());

        arquivo = new MockMultipartFile("arquivo", "laudo.pdf", "application/pdf", "conteudo".getBytes());
    }

    @Test
    void salvar_deveRetornarDocumentoSalvo_quandoDadosValidos() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(documentoRepository.save(any(Documento.class))).thenReturn(documento);

        Documento resultado = service.salvar(arquivo, 1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNomeOriginal()).isEqualTo("laudo.pdf");
        assertThat(resultado.getMimeType()).isEqualTo("application/pdf");
        assertThat(resultado.getPessoa().getIdPessoa()).isEqualTo(1);
        verify(documentoRepository).save(any(Documento.class));
    }

    @Test
    void salvar_deveLancarExcecao_quandoPessoaNaoEncontrada() {
        when(pessoaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.salvar(arquivo, 99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listarPorPessoa_deveRetornarListaDeDocumentos() {
        when(documentoRepository.findByPessoaIdPessoa(1)).thenReturn(List.of(documento));

        List<Documento> resultado = service.listarPorPessoa(1);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNomeOriginal()).isEqualTo("laudo.pdf");
    }

    @Test
    void listarPorPessoa_deveRetornarListaVazia_quandoNaoHaDocumentos() {
        when(documentoRepository.findByPessoaIdPessoa(1)).thenReturn(List.of());

        List<Documento> resultado = service.listarPorPessoa(1);

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarDocumento_quandoExistente() {
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));

        Documento resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNomeOriginal()).isEqualTo("laudo.pdf");
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(documentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void obterDados_deveRetornarBytes_quandoDocumentoExistente() {
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));

        byte[] dados = service.obterDados(1);

        assertThat(dados).isEqualTo("conteudo".getBytes());
    }

    @Test
    void deletarPorId_deveDeletar_quandoExistente() {
        when(documentoRepository.findById(1)).thenReturn(Optional.of(documento));

        service.deletarPorId(1);

        verify(documentoRepository).delete(documento);
    }

    @Test
    void deletarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(documentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
