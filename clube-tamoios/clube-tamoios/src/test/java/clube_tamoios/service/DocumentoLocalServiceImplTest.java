package clube_tamoios.service;

import clube_tamoios.entity.Documento;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.service.implementations.DocumentoLocalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentoLocalServiceImplTest {

    @Mock
    private DocumentoRepository documentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @TempDir
    Path tempDir;

    private DocumentoLocalServiceImpl service;

    private Pessoa pessoa;
    private MockMultipartFile arquivo;

    @BeforeEach
    void setUp() {
        service = new DocumentoLocalServiceImpl(documentoRepository, pessoaRepository, tempDir.toString());

        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        arquivo = new MockMultipartFile("arquivo", "laudo.pdf", "application/pdf", "conteudo do arquivo".getBytes());
    }

    @Test
    void salvar_deveSalvarArquivoEmDisco_quandoDadosValidos() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(documentoRepository.save(any(Documento.class))).thenAnswer(inv -> inv.getArgument(0));

        Documento resultado = service.salvar(arquivo, 1, "laudo");

        assertThat(resultado.getDados()).isNull();
        assertThat(resultado.getNomeGerado()).isNotBlank();
        assertThat(Files.exists(Path.of(resultado.getNomeGerado()))).isTrue();
        verify(documentoRepository).save(any(Documento.class));
    }

    @Test
    void salvar_deveGravarConteudoCorretoNoDisco() throws IOException {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(documentoRepository.save(any(Documento.class))).thenAnswer(inv -> inv.getArgument(0));

        Documento resultado = service.salvar(arquivo, 1, "laudo");

        byte[] conteudoDisco = Files.readAllBytes(Path.of(resultado.getNomeGerado()));
        assertThat(conteudoDisco).isEqualTo("conteudo do arquivo".getBytes());
    }

    @Test
    void salvar_deveLancarExcecao_quandoPessoaNaoEncontrada() {
        when(pessoaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.salvar(arquivo, 99, "laudo"))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void obterDados_deveLerConteudoDoArquivoDoDisco() throws IOException {
        byte[] conteudo = "conteudo do laudo".getBytes();
        Path arquivoTemp = tempDir.resolve("laudo.pdf");
        Files.write(arquivoTemp, conteudo);

        Documento doc = new Documento();
        doc.setId(1);
        doc.setNomeGerado(arquivoTemp.toString());

        when(documentoRepository.findById(1)).thenReturn(Optional.of(doc));

        byte[] dados = service.obterDados(1);

        assertThat(dados).isEqualTo(conteudo);
    }

    @Test
    void obterDados_deveLancarExcecao_quandoDocumentoNaoEncontrado() {
        when(documentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obterDados(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }

    @Test
    void deletarPorId_deveDeletarArquivoDoDisco() throws IOException {
        byte[] conteudo = "conteudo para deletar".getBytes();
        Path arquivoTemp = tempDir.resolve("deletar.pdf");
        Files.write(arquivoTemp, conteudo);
        assertThat(Files.exists(arquivoTemp)).isTrue();

        Documento doc = new Documento();
        doc.setId(1);
        doc.setNomeGerado(arquivoTemp.toString());

        when(documentoRepository.findById(1)).thenReturn(Optional.of(doc));

        service.deletarPorId(1);

        assertThat(Files.exists(arquivoTemp)).isFalse();
        verify(documentoRepository).delete(doc);
    }

    @Test
    void deletarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(documentoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }

    @Test
    void listarPorPessoa_deveRetornarListaDeDocumentos() {
        Documento doc = new Documento();
        doc.setId(1);
        doc.setPessoa(pessoa);
        when(documentoRepository.findByPessoaIdPessoa(1)).thenReturn(List.of(doc));

        List<Documento> resultado = service.listarPorPessoa(1);

        assertThat(resultado).hasSize(1);
    }
}
