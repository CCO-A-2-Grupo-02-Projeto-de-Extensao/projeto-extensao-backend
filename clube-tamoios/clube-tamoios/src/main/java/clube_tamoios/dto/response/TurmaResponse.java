package clube_tamoios.dto.response;

public class TurmaResponse {

    private Integer id;
    private Integer idClasse;
    private String nomeClasse;
    private Integer idUnidade;
    private String nomeUnidade;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }

    public String getNomeClasse() { return nomeClasse; }
    public void setNomeClasse(String nomeClasse) { this.nomeClasse = nomeClasse; }

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }

    public String getNomeUnidade() { return nomeUnidade; }
    public void setNomeUnidade(String nomeUnidade) { this.nomeUnidade = nomeUnidade; }
}