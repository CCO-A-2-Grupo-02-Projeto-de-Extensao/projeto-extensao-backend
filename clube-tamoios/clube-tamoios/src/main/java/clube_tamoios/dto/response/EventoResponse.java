package clube_tamoios.dto.response;
import java.time.LocalDate;

public class EventoResponse {
    private Integer idEvento;
    private String nome;
    private String tipo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;

    // Getters e Setters omitidos por brevidade (gere-os normalmente)
    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}