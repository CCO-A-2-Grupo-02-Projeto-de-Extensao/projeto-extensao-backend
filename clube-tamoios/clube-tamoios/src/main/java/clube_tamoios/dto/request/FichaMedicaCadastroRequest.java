package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;

public class FichaMedicaCadastroRequest {

    @NotNull
    private Integer idPessoa;

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
