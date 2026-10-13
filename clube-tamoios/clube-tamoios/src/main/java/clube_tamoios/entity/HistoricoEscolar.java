package clube_tamoios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Historico_escolar",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Pessoa_idPessoa", "ano"}))
public class HistoricoEscolar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoricoEscolar")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Pessoa_idPessoa", nullable = false)
    private Pessoa pessoa;

    @Column(nullable = false)
    private Integer ano;

    @Column(length = 45)
    private String serie;

    @Column(nullable = false)
    private boolean obediencia;

    @Column(nullable = false)
    private boolean prestatividade;

    @Column(nullable = false)
    private boolean participacao;

    @ManyToOne
    @JoinColumn(name = "Documento_idBoletim")
    private Documento boletim;

    @ManyToOne
    @JoinColumn(name = "Documento_idDeclaracao")
    private Documento declaracao;

    @Column(nullable = false)
    private boolean outroClube;

    @Column(length = 100)
    private String clubeOrigem;

    @Column(nullable = false)
    private boolean classeConcluidaOrigem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public boolean isObediencia() {
        return obediencia;
    }

    public void setObediencia(boolean obediencia) {
        this.obediencia = obediencia;
    }

    public boolean isPrestatividade() {
        return prestatividade;
    }

    public void setPrestatividade(boolean prestatividade) {
        this.prestatividade = prestatividade;
    }

    public boolean isParticipacao() {
        return participacao;
    }

    public void setParticipacao(boolean participacao) {
        this.participacao = participacao;
    }

    public Documento getBoletim() {
        return boletim;
    }

    public void setBoletim(Documento boletim) {
        this.boletim = boletim;
    }

    public Documento getDeclaracao() {
        return declaracao;
    }

    public void setDeclaracao(Documento declaracao) {
        this.declaracao = declaracao;
    }

    public boolean isOutroClube() {
        return outroClube;
    }

    public void setOutroClube(boolean outroClube) {
        this.outroClube = outroClube;
    }

    public String getClubeOrigem() {
        return clubeOrigem;
    }

    public void setClubeOrigem(String clubeOrigem) {
        this.clubeOrigem = clubeOrigem;
    }

    public boolean isClasseConcluidaOrigem() {
        return classeConcluidaOrigem;
    }

    public void setClasseConcluidaOrigem(boolean classeConcluidaOrigem) {
        this.classeConcluidaOrigem = classeConcluidaOrigem;
    }
}
