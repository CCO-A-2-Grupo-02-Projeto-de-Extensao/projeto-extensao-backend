package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;

public class TurmaRequest {

    @NotNull(message = "O ID da classe é obrigatório")
    private Integer idClasse;

    @NotNull(message = "O ID da unidade é obrigatório")
    private Integer idUnidade;

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }
}