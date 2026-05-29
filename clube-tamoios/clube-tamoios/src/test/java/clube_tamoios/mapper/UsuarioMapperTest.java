package clube_tamoios.mapper;

import clube_tamoios.dto.response.LoginResponse;
import clube_tamoios.dto.response.UsuarioResponse;
import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    @DisplayName("Deve converter Usuario para UsuarioResponse")
    void deveConverterUsuarioParaResponse() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setCpf("123.456.789-00");

        Cargo cargo = new Cargo();
        cargo.setNome("Diretor Clube");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setEmail("joao@clube.com");
        usuario.setAtivo(true);
        usuario.setPessoa(pessoa);
        usuario.setCargo(cargo);

        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        assertEquals(1, response.getIdUsuario());
        assertEquals("joao@clube.com", response.getEmail());
        assertTrue(response.getAtivo());
        assertEquals("João Silva", response.getNomePessoa());
        assertEquals("123.456.789-00", response.getCpfPessoa());
        assertEquals("Diretor Clube", response.getNomeCargo());
    }

    @Test
    @DisplayName("Deve converter Usuario para UsuarioResponse sem Pessoa e Cargo")
    void deveConverterUsuarioSemPessoaECargo() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(2);
        usuario.setEmail("sem@clube.com");
        usuario.setAtivo(false);

        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        assertEquals(2, response.getIdUsuario());
        assertEquals("sem@clube.com", response.getEmail());
        assertFalse(response.getAtivo());
        assertNull(response.getNomePessoa());
        assertNull(response.getCpfPessoa());
        assertNull(response.getNomeCargo());
    }

    @Test
    @DisplayName("Deve converter Usuario para LoginResponse")
    void deveConverterUsuarioParaLoginResponse() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria Souza");

        Cargo cargo = new Cargo();
        cargo.setNome("Secretária");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(3);
        usuario.setEmail("maria@clube.com");
        usuario.setAtivo(true);
        usuario.setPessoa(pessoa);
        usuario.setCargo(cargo);

        LoginResponse response = UsuarioMapper.toLoginResponse(usuario);

        assertEquals(3, response.getIdUsuario());
        assertEquals("maria@clube.com", response.getEmail());
        assertTrue(response.getAtivo());
        assertEquals("Maria Souza", response.getNomePessoa());
        assertEquals("Secretária", response.getNomeCargo());
    }

    @Test
    @DisplayName("Deve converter Usuario para LoginResponse sem Pessoa e Cargo")
    void deveConverterUsuarioParaLoginResponseSemPessoaECargo() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(4);
        usuario.setEmail("vazio@clube.com");
        usuario.setAtivo(true);

        LoginResponse response = UsuarioMapper.toLoginResponse(usuario);

        assertEquals(4, response.getIdUsuario());
        assertNull(response.getNomePessoa());
        assertNull(response.getNomeCargo());
    }
}