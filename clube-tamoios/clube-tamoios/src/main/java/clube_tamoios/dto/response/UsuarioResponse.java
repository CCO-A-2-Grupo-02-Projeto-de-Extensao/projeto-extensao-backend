package clube_tamoios.dto.response;

public class UsuarioResponse {

    private Integer idUsuario;
    private String email;
    private Boolean ativo;
    private String nomePessoa;
    private String cpfPessoa;
    private String nomeCargo;

    public UsuarioResponse() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }

    public String getCpfPessoa() { return cpfPessoa; }
    public void setCpfPessoa(String cpfPessoa) { this.cpfPessoa = cpfPessoa; }

    public String getNomeCargo() { return nomeCargo; }
    public void setNomeCargo(String nomeCargo) { this.nomeCargo = nomeCargo; }
}
