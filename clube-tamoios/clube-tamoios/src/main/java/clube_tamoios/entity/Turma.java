package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTurma")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Classe_idClasse", nullable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "Unidade_idUnidade", nullable = false)
    private Unidade unidade;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
}