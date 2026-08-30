package clube_tamoios.service;

public interface TokenService {

    String gerar(String username, String role);

    String validar(String token);

    String extrairRole(String token);
}
