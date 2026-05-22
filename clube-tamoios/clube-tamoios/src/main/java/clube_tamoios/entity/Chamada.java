package clube_tamoios.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "chamada")
public class Chamada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChamada")
    private Integer idChamada;

    @ManyToOne
    @JoinColumn(name = "Evento_idEvento", nullable = false)
    private Evento evento;

    @Column(name = "data_chamada")
    private LocalDate dataChamada;

    @Column(name = "titulo", length = 100)
    private String titulo;

    // Getters e Setters
    public Integer getIdChamada() { return idChamada; }
    public void setIdChamada(Integer idChamada) { this.idChamada = idChamada; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public LocalDate getDataChamada() { return dataChamada; }
    public void setDataChamada(LocalDate dataChamada) { this.dataChamada = dataChamada; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}