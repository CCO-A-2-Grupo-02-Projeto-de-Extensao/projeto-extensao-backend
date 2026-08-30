package clube_tamoios.service;

import clube_tamoios.dto.request.LoginRequest;
import clube_tamoios.dto.request.UsuarioAtualizacaoRequest;
import clube_tamoios.dto.request.UsuarioCadastroRequest;
import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.dto.response.UsuarioResponse;
import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Usuario;
import clube_tamoios.exception.CredenciaisInvalidasException;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.exception.RegraDeNegocioException;
import clube_tamoios.repository.CargoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioAtivo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setCpf("123.456.789-00");

        Cargo cargo = new Cargo();
        cargo.setNome("Diretor");

        Usuario u = new Usuario();
        u.setIdUsuario(1);
        u.setEmail("joao@clube.com");
        u.setSenha("$2a$10$DBFC490wr.eMeDvHOSmB7O0fOroyVLsy4qxmcThxcYKpfUfLhtlbO");
        u.setAtivo(true);
        u.setPessoa(pessoa);
        u.setCargo(cargo);
        return u;
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarUsuarioComSucesso() {
        UsuarioCadastroRequest request = new UsuarioCadastroRequest();
        request.setEmail("novo@clube.com");
        request.setSenha("senha123");
        request.setIdPessoa(1);
        request.setIdCargo(1);

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(new Pessoa()));
        when(cargoRepository.findById(1)).thenReturn(Optional.of(new Cargo()));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtivo());

        UsuarioResponse response = service.cadastrar(request);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com e-mail já existente")
    void deveLancarExcecaoCadastroEmailDuplicado() {
        UsuarioCadastroRequest request = new UsuarioCadastroRequest();
        request.setEmail("existente@clube.com");

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> service.cadastrar(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com Pessoa inexistente")
    void deveLancarExcecaoCadastroPessoaInexistente() {
        UsuarioCadastroRequest request = new UsuarioCadastroRequest();
        request.setEmail("novo@clube.com");
        request.setIdPessoa(99);

        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(pessoaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.cadastrar(request));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com Cargo inexistente")
    void deveLancarExcecaoCadastroCargoInexistente() {
        UsuarioCadastroRequest request = new UsuarioCadastroRequest();
        request.setEmail("novo@clube.com");
        request.setIdPessoa(1);
        request.setIdCargo(99);

        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(new Pessoa()));
        when(cargoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.cadastrar(request));
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLoginComSucesso() {
        LoginRequest request = new LoginRequest();
        request.setEmail("joao@clube.com");
        request.setSenha("senha123");

        when(usuarioRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(usuarioAtivo()));
        when(passwordEncoder.matches(request.getSenha(), usuarioAtivo().getSenha())).thenReturn(true);
        when(tokenService.gerar(anyString(), anyString())).thenReturn("token-de-teste");

        LoginResponse response = service.login(request);

        assertNotNull(response);
        assertEquals("joao@clube.com", response.getEmail());
        assertNotNull(response.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção ao logar com credenciais inválidas")
    void deveLancarExcecaoLoginCredenciaisInvalidas() {
        LoginRequest request = new LoginRequest();
        request.setEmail("x@clube.com");
        request.setSenha("errada");

        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(CredenciaisInvalidasException.class, () -> service.login(request));
    }

    @Test
    @DisplayName("Deve lançar exceção ao logar com usuário inativo")
    void deveLancarExcecaoLoginUsuarioInativo() {
        Usuario inativo = usuarioAtivo();
        inativo.setAtivo(false);

        LoginRequest request = new LoginRequest();
        request.setEmail(inativo.getEmail());
        request.setSenha("senha123");

        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(inativo));
        when(passwordEncoder.matches(request.getSenha(), inativo.getSenha())).thenReturn(true);

        assertThrows(CredenciaisInvalidasException.class, () -> service.login(request));
    }

    @Test
    @DisplayName("Deve listar apenas usuários ativos")
    void deveListarAtivos() {
        when(usuarioRepository.findAllByAtivoTrue()).thenReturn(List.of(usuarioAtivo()));

        List<UsuarioResponse> lista = service.listarAtivos();

        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void deveListarTodos() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioAtivo(), usuarioAtivo()));

        List<UsuarioResponse> lista = service.listarTodos();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarPorId() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioAtivo()));

        UsuarioResponse response = service.buscarPorId(1);

        assertNotNull(response);
        assertEquals(1, response.getIdUsuario());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário com ID inexistente")
    void deveLancarExcecaoBuscarPorIdInexistente() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId(99));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        UsuarioAtualizacaoRequest request = new UsuarioAtualizacaoRequest();
        request.setEmail("joao@clube.com"); // mesmo e-mail, sem conflito
        request.setIdCargo(1);
        request.setSenha("novaSenha");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioAtivo()));
        when(cargoRepository.findById(1)).thenReturn(Optional.of(new Cargo()));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtivo());

        UsuarioResponse response = service.atualizar(1, request);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com e-mail já usado por outro usuário")
    void deveLancarExcecaoAtualizarEmailDuplicado() {
        UsuarioAtualizacaoRequest request = new UsuarioAtualizacaoRequest();
        request.setEmail("outro@clube.com");
        request.setIdCargo(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioAtivo()));
        when(usuarioRepository.existsByEmail("outro@clube.com")).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> service.atualizar(1, request));
    }

    @Test
    @DisplayName("Deve desativar usuário com sucesso")
    void deveDesativarUsuarioComSucesso() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioAtivo()));

        service.desativar(1);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao desativar usuário já inativo")
    void deveLancarExcecaoDesativarUsuarioJaInativo() {
        Usuario inativo = usuarioAtivo();
        inativo.setAtivo(false);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(inativo));

        assertThrows(RegraDeNegocioException.class, () -> service.desativar(1));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        service.deletar(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void deveLancarExcecaoDeletarInexistente() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.deletar(99));
    }
}