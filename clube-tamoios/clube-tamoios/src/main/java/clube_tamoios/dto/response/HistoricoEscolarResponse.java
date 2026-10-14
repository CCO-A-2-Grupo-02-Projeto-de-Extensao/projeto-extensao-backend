package clube_tamoios.dto.response;

public class HistoricoEscolarResponse {

    private Integer id;
    private Integer idPessoa;
    private Integer ano;
    private String serie;
    private boolean obediencia;
    private boolean prestatividade;
    private boolean participacao;
    private Integer idBoletim;
    private String nomeBoletim;
    private Integer idDeclaracao;
    private String nomeDeclaracao;
    private boolean outroClube;
    private String clubeOrigem;
    private boolean classeConcluidaOrigem;

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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

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

    public String getNomeBoletim() {
        return nomeBoletim;
    }

    public void setNomeBoletim(String nomeBoletim) {
        this.nomeBoletim = nomeBoletim;
    }

    public Integer getIdDeclaracao() {
        return idDeclaracao;
    }

    public void setIdDeclaracao(Integer idDeclaracao) {
        this.idDeclaracao = idDeclaracao;
    }

    public String getNomeDeclaracao() {
        return nomeDeclaracao;
    }

    public void setNomeDeclaracao(String nomeDeclaracao) {
        this.nomeDeclaracao = nomeDeclaracao;
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
