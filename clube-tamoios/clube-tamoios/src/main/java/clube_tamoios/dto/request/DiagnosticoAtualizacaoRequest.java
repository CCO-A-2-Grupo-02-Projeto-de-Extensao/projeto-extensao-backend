package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;

public class DiagnosticoAtualizacaoRequest {

    @NotNull
    private Integer idComorbidade;

    private Integer idDocumento;

    public Integer getIdComorbidade() {
        return idComorbidade;
    }

    public void setIdComorbidade(Integer idComorbidade) {
        this.idComorbidade = idComorbidade;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }
}
