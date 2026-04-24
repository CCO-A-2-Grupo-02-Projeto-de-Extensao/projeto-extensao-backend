package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa")
    private Integer idPessoa;

    @ManyToOne
    @JoinColumn(name = "Classe_idClasse")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "Genero_idGenero")
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "Unidade_idUnidade")
    private Unidade unidade;

    @Column(length = 45)
    private String nome;

    @Column(length = 45)
    private String cpf;

    @Column(length = 45)
    private String rg;

    @Column(name = "data_nascimento", length = 45)
    private String dataNascimento;

    @Column(length = 45)
    private String telefone;

    @Column(name = "isDesbravador")
    private Boolean isDesbravador;

    @Column(name = "idResponsavel")
    private Integer idResponsavel;

    @ManyToOne
    @JoinColumn(name = "fkCargo")
    private Cargo cargo;

    public Pessoa() {}

    public Integer getIdPessoa() { return idPessoa; }
    public void setIdPessoa(Integer idPessoa) { this.idPessoa = idPessoa; }

    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }

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

    public Boolean getDesbravador() {
        return isDesbravador;
    }
    public void setDesbravador(Boolean desbravador) {
        isDesbravador = desbravador;
    }

    public Integer getIdResponsavel() { return idResponsavel; }
    public void setIdResponsavel(Integer idResponsavel) { this.idResponsavel = idResponsavel; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }
}
