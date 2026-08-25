package clube_tamoios.controller;

import clube_tamoios.dto.request.LoginRequest;
import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return usuarioService.login(request);
    }
}
