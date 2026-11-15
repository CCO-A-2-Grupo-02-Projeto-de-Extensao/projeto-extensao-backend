package clube_tamoios.service;

import clube_tamoios.entity.Pessoa;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.CargoRepository;
import clube_tamoios.repository.ClasseRepository;
import clube_tamoios.repository.GeneroRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.UnidadeRepository;
import clube_tamoios.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentimentoServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ClasseRepository classeRepository;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private UnidadeRepository unidadeRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PessoaService service;

    private Pessoa desbravadorComResponsavel() {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNomeResponsavel1("Adriano Monteiro");
        return pessoa;
    }

    @Test
    @DisplayName("Deve registrar o consentimento com o nome do responsável como está no cadastro")
    void deveRegistrarConsentimento() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(desbravadorComResponsavel()));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Pessoa resultado = service.registrarConsentimento(1, "  adriano monteiro ");

        assertEquals("Adriano Monteiro", resultado.getConsentimentoResponsavel());
        assertEquals(PessoaService.VERSAO_TERMO_CONSENTIMENTO, resultado.getConsentimentoVersao());
        assertNotNull(resultado.getConsentimentoEm());
    }

    @Test
    @DisplayName("Deve recusar consentimento de quem não é responsável cadastrado")
    void deveRecusarConsentimentoDeQuemNaoEhResponsavel() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(desbravadorComResponsavel()));

        assertThrows(RegraDeNegocioException.class,
                () -> service.registrarConsentimento(1, "Fulano"));
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve limpar o consentimento ao revogar")
    void deveRevogarConsentimento() {
        Pessoa pessoa = desbravadorComResponsavel();
        pessoa.setConsentimentoResponsavel("Adriano Monteiro");
        pessoa.setConsentimentoEm(LocalDateTime.now());
        pessoa.setConsentimentoVersao(PessoaService.VERSAO_TERMO_CONSENTIMENTO);
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Pessoa resultado = service.revogarConsentimento(1);

        assertNull(resultado.getConsentimentoResponsavel());
        assertNull(resultado.getConsentimentoEm());
        assertNull(resultado.getConsentimentoVersao());
    }
}
