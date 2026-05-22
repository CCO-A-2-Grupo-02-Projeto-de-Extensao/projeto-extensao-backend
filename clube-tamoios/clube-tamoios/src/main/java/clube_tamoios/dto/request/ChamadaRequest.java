package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ChamadaRequest {
    @NotNull private Integer idEvento;
    private LocalDate dataChamada;
    private String titulo;

    // Getters e Setters...
    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
    public LocalDate getDataChamada() { return dataChamada; }
    public void setDataChamada(LocalDate dataChamada) { this.dataChamada = dataChamada; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}