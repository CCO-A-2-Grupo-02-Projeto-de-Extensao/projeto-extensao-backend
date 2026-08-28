package clube_tamoios.config;

import clube_tamoios.entity.Usuario;
import clube_tamoios.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class MigracaoSenhasRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MigracaoSenhasRunner.class);

    private static final Pattern JA_E_BCRYPT = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public MigracaoSenhasRunner(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Usuario> pendentes = usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getSenha() != null && !usuario.getSenha().isBlank())
                .filter(usuario -> !JA_E_BCRYPT.matcher(usuario.getSenha()).matches())
                .toList();

        if (pendentes.isEmpty()) {
            return;
        }

        pendentes.forEach(usuario -> usuario.setSenha(passwordEncoder.encode(usuario.getSenha())));
        usuarioRepository.saveAll(pendentes);

        log.warn("Migração de senhas: {} usuário(s) estavam em texto puro e foram convertidos para BCrypt.",
                pendentes.size());
    }
}
