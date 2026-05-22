package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class OcorrenciaCadastroRequest {

    @NotNull
    private LocalDate data;

    private String descricao;

    @NotNull
    private Integer idPessoa;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
