package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Unidade")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidade")
    private Integer idUnidade;

    @Column(length = 255)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "Genero_idGenero")
    private Genero genero;

    // Texto livre ("10 - 12", ">15") porque o clube escreve a faixa de idades
    // nesse formato nos quadros das unidades, e nem toda unidade tem limite
    // superior.
    @Column(name = "faixa_etaria", length = 45)
    private String faixaEtaria;

    // Conselheiro é um membro do clube; nulo enquanto a unidade não tiver um.
    @ManyToOne
    @JoinColumn(name = "Pessoa_idConselheiro")
    private Pessoa conselheiro;

    public Unidade() {}

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public Pessoa getConselheiro() { return conselheiro; }
    public void setConselheiro(Pessoa conselheiro) { this.conselheiro = conselheiro; }
}
