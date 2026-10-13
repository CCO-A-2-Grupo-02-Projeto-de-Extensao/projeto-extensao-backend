package clube_tamoios.service;

import clube_tamoios.dto.request.EspecialidadeConcluidaRequest;
import clube_tamoios.entity.Especialidade;
import clube_tamoios.entity.EspecialidadeConcluida;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.EspecialidadeConcluidaRepository;
import clube_tamoios.repository.EspecialidadeRepository;
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
class EspecialidadeConcluidaServiceTest {

    @Mock
    private EspecialidadeConcluidaRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private EspecialidadeConcluidaService service;

    private Pessoa pessoa;
    private Especialidade especialidade;
    private EspecialidadeConcluidaRequest request;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        especialidade = new Especialidade();
        especialidade.setIdEspecialidade(22);

        request = new EspecialidadeConcluidaRequest();
        request.setIdPessoa(1);
        request.setIdEspecialidade(22);
        request.setAno(2025);
    }

    @Test
    void cadastrar_deveSalvar_quandoAindaNaoConcluida() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(especialidadeRepository.findById(22)).thenReturn(Optional.of(especialidade));
        when(repository.existsByPessoaIdPessoaAndEspecialidadeIdEspecialidade(1, 22)).thenReturn(false);
        when(repository.save(any(EspecialidadeConcluida.class))).thenAnswer(inv -> inv.getArgument(0));

        EspecialidadeConcluida resultado = service.cadastrar(request);

        assertThat(resultado.getPessoa()).isSameAs(pessoa);
        assertThat(resultado.getEspecialidade()).isSameAs(especialidade);
        assertThat(resultado.getAno()).isEqualTo(2025);
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoJaConcluida() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(especialidadeRepository.findById(22)).thenReturn(Optional.of(especialidade));
        when(repository.existsByPessoaIdPessoaAndEspecialidadeIdEspecialidade(1, 22)).thenReturn(true);

        assertThatThrownBy(() -> service.cadastrar(request))
                .isInstanceOf(RegraDeNegocioException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoEspecialidadeNaoEncontrada() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(especialidadeRepository.findById(22)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cadastrar(request))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrada() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
