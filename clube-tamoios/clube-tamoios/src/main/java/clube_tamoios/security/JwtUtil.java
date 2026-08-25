package clube_tamoios.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = resolverSecret();

        private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static String resolverSecret() {
        String env = System.getenv("JWT_SECRET");
        if (env != null && !env.isBlank()) {
            return env;
        }
        byte[] segredo = new byte[32];
        new SecureRandom().nextBytes(segredo);
        return java.util.Base64.getEncoder().encodeToString(segredo);
    }

    public static String gerarToken(String username) {
        return gerarToken(username, "DIRETOR");
    }

    public static String gerarToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(KEY)
                .compact();
    }

    public static String validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static String extrairRole(String token) {
        Object role = Jwts.parserBuilder().setSigningKey(KEY).build()
                .parseClaimsJws(token).getBody().get("role");
        return role instanceof String ? (String) role : "DESBRAVADOR";
    }
}