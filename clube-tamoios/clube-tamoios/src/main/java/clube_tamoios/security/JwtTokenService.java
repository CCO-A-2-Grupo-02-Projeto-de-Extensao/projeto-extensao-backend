package clube_tamoios.security;

import clube_tamoios.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements TokenService {

    static final String SECRET_PADRAO_DEV =
            "dev-local-somente-nao-usar-em-producao-1234567890";

    private static final long VALIDADE_MS = 3600000;

    private final Key chave;

    public JwtTokenService(@Value("${jwt.secret:" + SECRET_PADRAO_DEV + "}") String secret) {
        if (SECRET_PADRAO_DEV.equals(secret)) {
            System.err.println("AVISO: JWT_SECRET não definido, usando o segredo de desenvolvimento");
        }
        this.chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String gerar(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + VALIDADE_MS))
                .signWith(chave)
                .compact();
    }

    @Override
    public String validar(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String extrairRole(String token) {
        Object role = Jwts.parserBuilder().setSigningKey(chave).build()
                .parseClaimsJws(token).getBody().get("role");
        return role instanceof String ? (String) role : "DESBRAVADOR";
    }
}
