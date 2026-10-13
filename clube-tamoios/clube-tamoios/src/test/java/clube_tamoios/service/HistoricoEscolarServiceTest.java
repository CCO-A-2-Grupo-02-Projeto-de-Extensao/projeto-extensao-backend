package clube_tamoios.service;

import clube_tamoios.dto.request.HistoricoEscolarAtualizacaoRequest;
import clube_tamoios.dto.request.HistoricoEscolarCadastroRequest;
import clube_tamoios.entity.Documento;
import clube_tamoios.entity.HistoricoEscolar;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.DocumentoRepository;
import clube_tamoios.repository.HistoricoEscolarRepository;
import clube_tamoios.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoricoEscolarServiceTest {

    @Mock
    private HistoricoEscolarRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @InjectMocks
    private HistoricoEscolarService service;

    private Pessoa pessoa;
    private HistoricoEscolarCadastroRequest request;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        request = new HistoricoEscolarCadastroRequest();
        request.setIdPessoa(1);
        request.setAno(2025);
        request.setSerie("7º ano");
        request.setObediencia(true);
    }

    @Test
    void cadastrar_deveSalvarHistorico_quandoAnoAindaNaoExiste() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(repository.existsByPessoaIdPessoaAndAno(1, 2025)).thenReturn(false);
        when(repository.save(any(HistoricoEscolar.class))).thenAnswer(inv -> inv.getArgument(0));

        HistoricoEscolar resultado = service.cadastrar(request);

        assertThat(resultado.getPessoa()).isSameAs(pessoa);
        assertThat(resultado.getAno()).isEqualTo(2025);
        assertThat(resultado.getSerie()).isEqualTo("7º ano");
        assertThat(resultado.isObediencia()).isTrue();
        assertThat(resultado.isParticipacao()).isFalse();
        assertThat(resultado.getBoletim()).isNull();
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoAnoJaExiste() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(repository.existsByPessoaIdPessoaAndAno(1, 2025)).thenReturn(true);

        assertThatThrownBy(() -> service.cadastrar(request))
                .isInstanceOf(RegraDeNegocioException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoPessoaNaoEncontrada() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cadastrar(request))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }

    @Test
    void atualizar_deveVincularDocumentosEDesvincularQuandoNulos() {
        Documento boletim = new Documento();
        boletim.setId(10);
        HistoricoEscolar historico = new HistoricoEscolar();
        historico.setId(5);
        historico.setPessoa(pessoa);
        historico.setAno(2025);
        historico.setDeclaracao(new Documento());

        HistoricoEscolarAtualizacaoRequest atualizacao = new HistoricoEscolarAtualizacaoRequest();
        atualizacao.setSerie("8º ano");
        atualizacao.setParticipacao(true);
        atualizacao.setIdBoletim(10);
        atualizacao.setOutroClube(true);
        atualizacao.setClubeOrigem("Clube Águias");
        atualizacao.setClasseConcluidaOrigem(true);

        when(repository.findById(5)).thenReturn(Optional.of(historico));
        when(documentoRepository.findById(10)).thenReturn(Optional.of(boletim));
        when(repository.save(historico)).thenReturn(historico);

        HistoricoEscolar resultado = service.atualizar(5, atualizacao);

        assertThat(resultado.getSerie()).isEqualTo("8º ano");
        assertThat(resultado.isParticipacao()).isTrue();
        assertThat(resultado.getBoletim()).isSameAs(boletim);
        assertThat(resultado.getDeclaracao()).isNull();
        assertThat(resultado.getAno()).isEqualTo(2025);
        assertThat(resultado.isOutroClube()).isTrue();
        assertThat(resultado.getClubeOrigem()).isEqualTo("Clube Águias");
        assertThat(resultado.isClasseConcluidaOrigem()).isTrue();
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
