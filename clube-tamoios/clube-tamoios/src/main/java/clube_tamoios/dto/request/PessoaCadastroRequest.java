package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PessoaCadastroRequest {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String cpf;
    private String rg;

    @NotBlank(message = "A data de nascimento é obrigatória")
    private String dataNascimento;

    private String telefone;
    private Boolean isDesbravador;

    private Integer idClasse;
    private Integer idGenero;
    private Integer idUnidade;
    private Integer idCargo;

    private String escola;
    private String serieEscolar;

    private String nomeResponsavel1;
    private String telefoneResponsavel1;
    private String rgResponsavel1;
    private String cpfResponsavel1;

    private String nomeResponsavel2;
    private String telefoneResponsavel2;
    private String rgResponsavel2;
    private String cpfResponsavel2;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Boolean getIsDesbravador() { return isDesbravador; }
    public void setIsDesbravador(Boolean isDesbravador) { this.isDesbravador = isDesbravador; }

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }

    public Integer getIdCargo() { return idCargo; }
    public void setIdCargo(Integer idCargo) { this.idCargo = idCargo; }

    public String getEscola() { return escola; }
    public void setEscola(String escola) { this.escola = escola; }

    public String getSerieEscolar() { return serieEscolar; }
    public void setSerieEscolar(String serieEscolar) { this.serieEscolar = serieEscolar; }

    public String getNomeResponsavel1() { return nomeResponsavel1; }
    public void setNomeResponsavel1(String nomeResponsavel1) { this.nomeResponsavel1 = nomeResponsavel1; }

    public String getTelefoneResponsavel1() { return telefoneResponsavel1; }
    public void setTelefoneResponsavel1(String telefoneResponsavel1) { this.telefoneResponsavel1 = telefoneResponsavel1; }

    public String getRgResponsavel1() { return rgResponsavel1; }
    public void setRgResponsavel1(String rgResponsavel1) { this.rgResponsavel1 = rgResponsavel1; }

    public String getCpfResponsavel1() { return cpfResponsavel1; }
    public void setCpfResponsavel1(String cpfResponsavel1) { this.cpfResponsavel1 = cpfResponsavel1; }

    public String getNomeResponsavel2() { return nomeResponsavel2; }
    public void setNomeResponsavel2(String nomeResponsavel2) { this.nomeResponsavel2 = nomeResponsavel2; }

    public String getTelefoneResponsavel2() { return telefoneResponsavel2; }
    public void setTelefoneResponsavel2(String telefoneResponsavel2) { this.telefoneResponsavel2 = telefoneResponsavel2; }

    public String getRgResponsavel2() { return rgResponsavel2; }
    public void setRgResponsavel2(String rgResponsavel2) { this.rgResponsavel2 = rgResponsavel2; }

    public String getCpfResponsavel2() { return cpfResponsavel2; }
    public void setCpfResponsavel2(String cpfResponsavel2) { this.cpfResponsavel2 = cpfResponsavel2; }
}
