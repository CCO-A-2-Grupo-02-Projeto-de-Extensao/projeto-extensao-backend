package clube_tamoios.service;

import org.springframework.stereotype.Service;
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
import clube_tamoios.mapper.UsuarioMapper;
import clube_tamoios.repository.CargoRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.UsuarioRepository;
import clube_tamoios.security.JwtUtil;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PessoaRepository pessoaRepository,
                          CargoRepository cargoRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
        this.cargoRepository = cargoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioCadastroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com o e-mail: " + request.getEmail());
        }

        Pessoa pessoa = pessoaRepository.findById(request.getIdPessoa())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada com id: " + request.getIdPessoa()));

        Cargo cargo = cargoRepository.findById(request.getIdCargo())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado com id: " + request.getIdCargo()));

        Usuario usuario = new Usuario();
        usuario.setPessoa(pessoa);
        usuario.setCargo(cargo);
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos");
        }

        if (!usuario.getAtivo()) {
            throw new CredenciaisInvalidasException("Usuário inativo. Entre em contato com a secretaria.");
        }

        LoginResponse response = UsuarioMapper.toLoginResponse(usuario);
        response.setToken(JwtUtil.gerarToken(usuario.getEmail(), normalizarCargo(usuario.getCargo().getNome())));
        return response;
    }

    private String normalizarCargo(String cargo) {
        return java.text.Normalizer.normalize(cargo, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase(java.util.Locale.ROOT)
                .replaceAll("[^A-Z0-9_]", "_");
    }

    public List<UsuarioResponse> listarAtivos() {
        return usuarioRepository.findAllByAtivoTrue()
                .stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + id));
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse atualizar(Integer id, UsuarioAtualizacaoRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + id));

        // Verifica conflito de e-mail apenas se mudou
        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com o e-mail: " + request.getEmail());
        }

        Cargo cargo = cargoRepository.findById(request.getIdCargo())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo não encontrado com id: " + request.getIdCargo()));

        usuario.setEmail(request.getEmail());
        usuario.setCargo(cargo);

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public void desativar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + id));

        if (!usuario.getAtivo()) {
            throw new RegraDeNegocioException("Usuário já está inativo.");
        }

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
