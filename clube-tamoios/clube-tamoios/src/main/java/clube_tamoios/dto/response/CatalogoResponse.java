package clube_tamoios.dto.response;

// DTO genérico pras tabelas de referência somente-leitura (Classe, Cargo,
// Gênero, Unidade) que alimentam os dropdowns do formulário de cadastro.
public class CatalogoResponse {

    private Integer id;
    private String nome;

    public CatalogoResponse() {}

    public CatalogoResponse(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
