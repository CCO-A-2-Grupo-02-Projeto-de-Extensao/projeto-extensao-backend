package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Classe")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClasse")
    private Integer idClasse;

    @Column(length = 255)
    private String nome;

    public Classe() {}

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
