package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;

public class PresencaRequest {
    @NotNull private Integer idChamada;
    @NotNull private Integer idPessoa;
    @NotNull private Boolean presente;

    // Getters e Setters...
    public Integer getIdChamada() { return idChamada; }
    public void setIdChamada(Integer idChamada) { this.idChamada = idChamada; }
    public Integer getIdPessoa() { return idPessoa; }
    public void setIdPessoa(Integer idPessoa) { this.idPessoa = idPessoa; }
    public Boolean getPresente() { return presente; }
    public void setPresente(Boolean presente) { this.presente = presente; }
}