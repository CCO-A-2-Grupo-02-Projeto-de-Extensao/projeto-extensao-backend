package clube_tamoios.dto.request;

// idUnidade nulo tira a pessoa da unidade, mantendo-a na classe.
public class VinculoUnidadeRequest {

    private Integer idUnidade;

    public Integer getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Integer idUnidade) { this.idUnidade = idUnidade; }
}
