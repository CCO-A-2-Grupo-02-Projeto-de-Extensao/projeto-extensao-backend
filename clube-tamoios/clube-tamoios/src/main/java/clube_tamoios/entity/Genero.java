package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenero")
    private Integer idGenero;

    @Column(length = 45)
    private String nome;

    public Genero() {}

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
