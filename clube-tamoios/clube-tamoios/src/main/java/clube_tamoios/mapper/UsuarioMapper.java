package clube_tamoios.mapper;

import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.dto.response.UsuarioResponse;
import clube_tamoios.entity.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setEmail(usuario.getEmail());
        response.setAtivo(usuario.getAtivo());

        if (usuario.getPessoa() != null) {
            response.setNomePessoa(usuario.getPessoa().getNome());
            response.setCpfPessoa(usuario.getPessoa().getCpf());
        }

        if (usuario.getCargo() != null) {
            response.setNomeCargo(usuario.getCargo().getNome());
        }

        return response;
    }

    public static LoginResponse toLoginResponse(Usuario usuario) {
        LoginResponse response = new LoginResponse();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setEmail(usuario.getEmail());
        response.setAtivo(usuario.getAtivo());

        // O front usa o idPessoa para saber qual membro da listagem e o proprio
        // usuario logado — e assim impedir que ele se desative.
        if (usuario.getPessoa() != null) {
            response.setIdPessoa(usuario.getPessoa().getIdPessoa());
            response.setNomePessoa(usuario.getPessoa().getNome());
        }

        if (usuario.getCargo() != null) {
            response.setNomeCargo(usuario.getCargo().getNome());
        }

        return response;
    }
}
