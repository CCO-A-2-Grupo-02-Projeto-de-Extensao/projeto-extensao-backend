package clube_tamoios.dto.response;

// Diferente do CatalogoResponse (id + nome) que alimenta os dropdowns: aqui vai
// o que a tabela de Unidades da classe precisa mostrar por linha.
public class UnidadeDetalheResponse {

    private Integer id;
    private String nome;
    private String faixaEtaria;
    private Integer idGenero;
    private String nomeGenero;
    private Integer idConselheiro;
    private String nomeConselheiro;
    private Long quantidadeDesbravadores;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public Integer getIdGenero() { return idGenero; }
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    public String getNomeGenero() { return nomeGenero; }
    public void setNomeGenero(String nomeGenero) { this.nomeGenero = nomeGenero; }

    public Integer getIdConselheiro() { return idConselheiro; }
    public void setIdConselheiro(Integer idConselheiro) { this.idConselheiro = idConselheiro; }

    public String getNomeConselheiro() { return nomeConselheiro; }
    public void setNomeConselheiro(String nomeConselheiro) { this.nomeConselheiro = nomeConselheiro; }

    public Long getQuantidadeDesbravadores() { return quantidadeDesbravadores; }
    public void setQuantidadeDesbravadores(Long quantidadeDesbravadores) { this.quantidadeDesbravadores = quantidadeDesbravadores; }
}
