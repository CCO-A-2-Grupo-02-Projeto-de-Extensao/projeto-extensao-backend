package clube_tamoios.dto.request;

// idClasse nulo é intencional: é assim que a tela remove alguém da classe sem
// apagar a pessoa do clube.
public class VinculoClasseRequest {

    private Integer idClasse;

    public Integer getIdClasse() { return idClasse; }
    public void setIdClasse(Integer idClasse) { this.idClasse = idClasse; }
}
