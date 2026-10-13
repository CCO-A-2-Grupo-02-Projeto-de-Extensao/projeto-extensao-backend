package clube_tamoios.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Classe")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClasse")
    private Integer idClasse;

    @Column(length = 255)
    private String nome;

    // A tabela Disciplina é o vínculo classe <-> especialidade do modelo. Não
    // vira entity própria porque não carrega nenhum atributo além das duas
    // chaves.
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("nome ASC")
    @JoinTable(
            name = "Disciplina",
            joinColumns = @JoinColumn(name = "Classe_idClasse"),
            inverseJoinColumns = @JoinColumn(name = "Especialidade_idEspecialidade")
    )
    private Set<Especialidade> especialidades = new LinkedHashSet<>();

    public Classe() {}

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Set<Especialidade> getEspecialidades() { return especialidades; }
    public void setEspecialidades(Set<Especialidade> especialidades) { this.especialidades = especialidades; }
}
