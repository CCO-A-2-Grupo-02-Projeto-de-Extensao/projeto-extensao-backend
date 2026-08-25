package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EspecialidadeRequest {

    @NotBlank(message = "O nome da especialidade é obrigatório")
    @Size(max = 45, message = "O nome deve ter no máximo 45 caracteres")
    private String nome;

    @Size(max = 60, message = "A categoria deve ter no máximo 60 caracteres")
    private String categoria;

    private String descricao;

    @Size(max = 500, message = "O caminho da imagem deve ter no máximo 500 caracteres")
    private String imagem;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}
