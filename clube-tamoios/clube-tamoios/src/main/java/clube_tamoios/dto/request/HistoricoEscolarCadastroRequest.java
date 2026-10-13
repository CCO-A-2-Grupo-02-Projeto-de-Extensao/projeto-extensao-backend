package clube_tamoios.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class HistoricoEscolarCadastroRequest extends HistoricoEscolarAtualizacaoRequest {

    @NotNull
    private Integer idPessoa;

    @NotNull
    @Min(1950)
    @Max(2100)
    private Integer ano;

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
