package clube_tamoios.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean ativo = true;

    @Column(length = 150)
    private String escola;

    @Column(name = "serie_escolar", length = 45)
    private String serieEscolar;

    @Column(name = "nome_responsavel1", length = 100)
    private String nomeResponsavel1;

    @Column(name = "telefone_responsavel1", length = 20)
    private String telefoneResponsavel1;

    @Column(name = "rg_responsavel1", length = 20)
    private String rgResponsavel1;

    @Column(name = "cpf_responsavel1", length = 14)
    private String cpfResponsavel1;

    @Column(name = "nome_responsavel2", length = 100)
    private String nomeResponsavel2;

    @Column(name = "telefone_responsavel2", length = 20)
    private String telefoneResponsavel2;

    @Column(name = "rg_responsavel2", length = 20)
    private String rgResponsavel2;

    @Column(name = "cpf_responsavel2", length = 14)
    private String cpfResponsavel2;

    @Column(name = "consentimento_responsavel", length = 100)
    private String consentimentoResponsavel;

    @Column(name = "consentimento_em")
    private LocalDateTime consentimentoEm;

    @Column(name = "consentimento_versao", length = 20)
    private String consentimentoVersao;

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

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

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

    public String getConsentimentoResponsavel() { return consentimentoResponsavel; }
    public void setConsentimentoResponsavel(String consentimentoResponsavel) { this.consentimentoResponsavel = consentimentoResponsavel; }

    public LocalDateTime getConsentimentoEm() { return consentimentoEm; }
    public void setConsentimentoEm(LocalDateTime consentimentoEm) { this.consentimentoEm = consentimentoEm; }

    public String getConsentimentoVersao() { return consentimentoVersao; }
    public void setConsentimentoVersao(String consentimentoVersao) { this.consentimentoVersao = consentimentoVersao; }
}
