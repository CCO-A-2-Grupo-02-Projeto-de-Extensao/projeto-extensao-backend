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

    public Unidade() {}

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
}
