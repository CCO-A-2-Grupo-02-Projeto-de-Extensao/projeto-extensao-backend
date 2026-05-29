package clube_tamoios.service;

import clube_tamoios.dto.request.FichaMedicaCadastroRequest;
import clube_tamoios.entity.FichaMedica;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.FichaMedicaRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FichaMedicaServiceTest {

    @Mock
    private FichaMedicaRepository fichaMedicaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private FichaMedicaService service;

    private Pessoa pessoa;
    private FichaMedica ficha;
    private FichaMedicaCadastroRequest request;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome("Carlos Silva");

        ficha = new FichaMedica();
        ficha.setId(1);
        ficha.setPessoa(pessoa);

        request = new FichaMedicaCadastroRequest();
        request.setIdPessoa(1);
    }

    @Test
    void criar_deveRetornarFichaSalva_quandoPessoaSemFicha() {
        when(fichaMedicaRepository.existsByPessoaIdPessoa(1)).thenReturn(false);
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(fichaMedicaRepository.save(any(FichaMedica.class))).thenReturn(ficha);

        FichaMedica resultado = service.criar(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getPessoa().getIdPessoa()).isEqualTo(1);
        verify(fichaMedicaRepository).save(any(FichaMedica.class));
    }

    @Test
    void criar_deveLancarExcecao_quandoPessoaJaPossuiFicha() {
        when(fichaMedicaRepository.existsByPessoaIdPessoa(1)).thenReturn(true);

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessageContaining("já possui ficha médica");
    }

    @Test
    void criar_deveLancarExcecao_quandoPessoaNaoEncontrada() {
        when(fichaMedicaRepository.existsByPessoaIdPessoa(1)).thenReturn(false);
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("1");
    }

    @Test
    void buscarPorId_deveRetornarFicha_quandoExistente() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));

        FichaMedica resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrada() {
        when(fichaMedicaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void buscarPorPessoa_deveRetornarFicha_quandoExistente() {
        when(fichaMedicaRepository.findByPessoaIdPessoa(1)).thenReturn(Optional.of(ficha));

        FichaMedica resultado = service.buscarPorPessoa(1);

        assertThat(resultado.getPessoa().getIdPessoa()).isEqualTo(1);
    }

    @Test
    void buscarPorPessoa_deveLancarExcecao_quandoNaoEncontrada() {
        when(fichaMedicaRepository.findByPessoaIdPessoa(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorPessoa(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(fichaMedicaRepository.findById(1)).thenReturn(Optional.of(ficha));

        service.deletar(1);

        verify(fichaMedicaRepository).delete(ficha);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrada() {
        when(fichaMedicaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
