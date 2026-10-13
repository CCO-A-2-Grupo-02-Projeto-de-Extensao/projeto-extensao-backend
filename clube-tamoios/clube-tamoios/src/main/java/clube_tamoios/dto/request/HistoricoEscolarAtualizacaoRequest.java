package clube_tamoios.dto.request;

import jakarta.validation.constraints.Size;

public class HistoricoEscolarAtualizacaoRequest {

    @Size(max = 45)
    private String serie;

    private boolean obediencia;

    private boolean prestatividade;

    private boolean participacao;

    private Integer idBoletim;

    private Integer idDeclaracao;

    private boolean outroClube;

    @Size(max = 100)
    private String clubeOrigem;

    private boolean classeConcluidaOrigem;

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public boolean isObediencia() {
        return obediencia;
    }

    public void setObediencia(boolean obediencia) {
        this.obediencia = obediencia;
    }

    public boolean isPrestatividade() {
        return prestatividade;
    }

    public void setPrestatividade(boolean prestatividade) {
        this.prestatividade = prestatividade;
    }

    public boolean isParticipacao() {
        return participacao;
    }

    public void setParticipacao(boolean participacao) {
        this.participacao = participacao;
    }

    public Integer getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(Integer idBoletim) {
        this.idBoletim = idBoletim;
    }

    public Integer getIdDeclaracao() {
        return idDeclaracao;
    }

    public void setIdDeclaracao(Integer idDeclaracao) {
        this.idDeclaracao = idDeclaracao;
    }

    public boolean isOutroClube() {
        return outroClube;
    }

    public void setOutroClube(boolean outroClube) {
        this.outroClube = outroClube;
    }

    public String getClubeOrigem() {
        return clubeOrigem;
    }

    public void setClubeOrigem(String clubeOrigem) {
        this.clubeOrigem = clubeOrigem;
    }

    public boolean isClasseConcluidaOrigem() {
        return classeConcluidaOrigem;
    }

    public void setClasseConcluidaOrigem(boolean classeConcluidaOrigem) {
        this.classeConcluidaOrigem = classeConcluidaOrigem;
    }
}
