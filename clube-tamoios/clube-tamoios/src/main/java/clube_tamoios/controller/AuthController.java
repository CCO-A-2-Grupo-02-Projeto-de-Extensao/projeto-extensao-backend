package clube_tamoios.controller;

import clube_tamoios.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> dados) {

        if ("admin".equals(dados.get("username")) &&
                "123".equals(dados.get("password"))) {

            return JwtUtil.gerarToken(dados.get("username"));
        }

        throw new RuntimeException("Login inválido");
    }
}
