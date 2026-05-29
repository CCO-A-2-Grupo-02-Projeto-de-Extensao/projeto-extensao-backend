package clube_tamoios.service;

import clube_tamoios.dto.request.OcorrenciaCadastroRequest;
import clube_tamoios.entity.Ocorrencia;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.OcorrenciaRepository;
import clube_tamoios.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OcorrenciaServiceTest {

    @Mock
    private OcorrenciaRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private OcorrenciaService service;

    private Pessoa pessoa;
    private Ocorrencia ocorrencia;
    private OcorrenciaCadastroRequest request;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        ocorrencia = new Ocorrencia();
        ocorrencia.setId(1);
        ocorrencia.setData(LocalDate.of(2026, 5, 22));
        ocorrencia.setDescricao("Briga durante atividade");
        ocorrencia.setPessoa(pessoa);

        request = new OcorrenciaCadastroRequest();
        request.setData(LocalDate.of(2026, 5, 22));
        request.setDescricao("Briga durante atividade");
        request.setIdPessoa(1);
    }

    @Test
    void cadastrar_deveRetornarOcorrenciaSalva_quandoDadosValidos() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(repository.save(any(Ocorrencia.class))).thenReturn(ocorrencia);

        Ocorrencia resultado = service.cadastrar(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getDescricao()).isEqualTo("Briga durante atividade");
        assertThat(resultado.getPessoa().getIdPessoa()).isEqualTo(1);
        verify(repository).save(any(Ocorrencia.class));
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoPessoaNaoEncontrada() {
        when(pessoaRepository.findById(99)).thenReturn(Optional.empty());
        request.setIdPessoa(99);

        assertThatThrownBy(() -> service.cadastrar(request))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listar_deveRetornarTodasAsOcorrencias() {
        when(repository.findAll()).thenReturn(List.of(ocorrencia));

        List<Ocorrencia> resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescricao()).isEqualTo("Briga durante atividade");
    }

    @Test
    void listarPorPessoa_deveRetornarOcorrenciasDaPessoa() {
        when(repository.findByPessoaIdPessoa(1)).thenReturn(List.of(ocorrencia));

        List<Ocorrencia> resultado = service.listarPorPessoa(1);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPessoa().getIdPessoa()).isEqualTo(1);
    }

    @Test
    void listarPorPessoa_deveRetornarListaVazia_quandoNaoHaOcorrencias() {
        when(repository.findByPessoaIdPessoa(99)).thenReturn(List.of());

        List<Ocorrencia> resultado = service.listarPorPessoa(99);

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarOcorrencia_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(ocorrencia));

        Ocorrencia resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getDescricao()).isEqualTo("Briga durante atividade");
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveAtualizarCampos_quandoDadosValidos() {
        OcorrenciaCadastroRequest novoRequest = new OcorrenciaCadastroRequest();
        novoRequest.setData(LocalDate.of(2026, 6, 1));
        novoRequest.setDescricao("Descrição atualizada");
        novoRequest.setIdPessoa(1);

        when(repository.findById(1)).thenReturn(Optional.of(ocorrencia));
        when(repository.save(any(Ocorrencia.class))).thenReturn(ocorrencia);

        Ocorrencia resultado = service.atualizar(1, novoRequest);

        assertThat(resultado).isNotNull();
        verify(repository).save(ocorrencia);
    }

    @Test
    void atualizar_deveLancarExcecao_quandoOcorrenciaNaoEncontrada() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, request))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveTrocarPessoa_quandoIdPessoaDiferente() {
        Pessoa outraPessoa = new Pessoa();
        outraPessoa.setIdPessoa(2);
        outraPessoa.setNome("Ana Lima");

        OcorrenciaCadastroRequest novoRequest = new OcorrenciaCadastroRequest();
        novoRequest.setData(LocalDate.of(2026, 6, 1));
        novoRequest.setDescricao("Nova descrição");
        novoRequest.setIdPessoa(2);

        when(repository.findById(1)).thenReturn(Optional.of(ocorrencia));
        when(pessoaRepository.findById(2)).thenReturn(Optional.of(outraPessoa));
        when(repository.save(any(Ocorrencia.class))).thenReturn(ocorrencia);

        service.atualizar(1, novoRequest);

        verify(pessoaRepository).findById(2);
        verify(repository).save(ocorrencia);
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(ocorrencia));

        service.deletar(1);

        verify(repository).delete(ocorrencia);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
