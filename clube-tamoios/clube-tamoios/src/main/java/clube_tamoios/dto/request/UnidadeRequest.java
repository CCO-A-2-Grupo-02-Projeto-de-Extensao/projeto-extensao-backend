package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UnidadeRequest {

    @NotBlank(message = "O nome da unidade é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    private Integer idGenero;

    @Size(max = 45, message = "A faixa etária deve ter no máximo 45 caracteres")
    private String faixaEtaria;

    private Integer idConselheiro;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public Integer getIdConselheiro() { return idConselheiro; }
    public void setIdConselheiro(Integer idConselheiro) { this.idConselheiro = idConselheiro; }
}
