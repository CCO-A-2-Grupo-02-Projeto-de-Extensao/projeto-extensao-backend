package clube_tamoios.dto.response;

public class PresencaResponse {

    private Integer id;
    private Integer idChamada;
    private Integer idPessoa;
    private String nomePessoa;
    private Boolean presente;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdChamada() { return idChamada; }
    public void setIdChamada(Integer idChamada) { this.idChamada = idChamada; }

    public Integer getIdPessoa() { return idPessoa; }
    public void setIdPessoa(Integer idPessoa) { this.idPessoa = idPessoa; }

    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }

    public Boolean getPresente() { return presente; }
    public void setPresente(Boolean presente) { this.presente = presente; }
}