package clube_tamoios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "presenca")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPresenca")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Chamada_idChamada", nullable = false)
    private Chamada chamada;

    @ManyToOne
    @JoinColumn(name = "Pessoa_idPessoa", nullable = false)
    private Pessoa pessoa;

    @Column(name = "presenca", columnDefinition = "TINYINT(1)")
    private Boolean presente;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Chamada getChamada() { return chamada; }
    public void setChamada(Chamada chamada) { this.chamada = chamada; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Boolean getPresente() { return presente; }
    public void setPresente(Boolean presente) { this.presente = presente; }
}