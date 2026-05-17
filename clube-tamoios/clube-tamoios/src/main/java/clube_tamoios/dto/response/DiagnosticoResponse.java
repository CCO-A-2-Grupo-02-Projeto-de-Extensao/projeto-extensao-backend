package clube_tamoios.dto.response;

public class DiagnosticoResponse {

    private Integer id;
    private Integer idFichaMedica;
    private Integer idComorbidade;
    private String nomeComorbidade;
    private Integer idDocumento;
    private String nomeDocumento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFichaMedica() {
        return idFichaMedica;
    }

    public void setIdFichaMedica(Integer idFichaMedica) {
        this.idFichaMedica = idFichaMedica;
    }

    public Integer getIdComorbidade() {
        return idComorbidade;
    }

    public void setIdComorbidade(Integer idComorbidade) {
        this.idComorbidade = idComorbidade;
    }

    public String getNomeComorbidade() {
        return nomeComorbidade;
    }

    public void setNomeComorbidade(String nomeComorbidade) {
        this.nomeComorbidade = nomeComorbidade;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }
}
