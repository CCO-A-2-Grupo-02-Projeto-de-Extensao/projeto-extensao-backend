package clube_tamoios.dto.response;
import java.time.LocalDate;

public class ChamadaResponse {
    private Integer idChamada;
    private Integer idEvento;
    private String nomeEvento;
    private LocalDate dataChamada;
    private String titulo;

    // Getters e Setters...
    public Integer getIdChamada() { return idChamada; }
    public void setIdChamada(Integer idChamada) { this.idChamada = idChamada; }
    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
    public String getNomeEvento() { return nomeEvento; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }
    public LocalDate getDataChamada() { return dataChamada; }
    public void setDataChamada(LocalDate dataChamada) { this.dataChamada = dataChamada; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}