package clube_tamoios.dto.response;

import java.util.List;

public class FichaMedicaResponse {

    private Integer id;
    private Integer idPessoa;
    private String nomePessoa;
    private List<MedicacaoResponse> medicacoes;
    private List<DiagnosticoResponse> diagnosticos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public List<MedicacaoResponse> getMedicacoes() {
        return medicacoes;
    }

    public void setMedicacoes(List<MedicacaoResponse> medicacoes) {
        this.medicacoes = medicacoes;
    }

    public List<DiagnosticoResponse> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<DiagnosticoResponse> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}
