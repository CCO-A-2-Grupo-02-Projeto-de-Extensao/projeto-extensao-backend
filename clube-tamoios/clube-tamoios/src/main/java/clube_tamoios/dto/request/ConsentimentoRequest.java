package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ConsentimentoRequest {

    @NotBlank(message = "Informe o responsável que autorizou")
    @Size(max = 100)
    private String nomeResponsavel;

    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }
}
