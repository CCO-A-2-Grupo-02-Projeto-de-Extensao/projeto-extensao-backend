package clube_tamoios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Diagnostico")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDiagnostico")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Ficha_medica_idFichaMedica", nullable = false)
    private FichaMedica fichaMedica;

    @ManyToOne
    @JoinColumn(name = "Comorbidade_idComorbidade", nullable = false)
    private Comorbidade comorbidade;

    @ManyToOne
    @JoinColumn(name = "Documento_idDocumento")
    private Documento documento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FichaMedica getFichaMedica() {
        return fichaMedica;
    }

    public void setFichaMedica(FichaMedica fichaMedica) {
        this.fichaMedica = fichaMedica;
    }

    public Comorbidade getComorbidade() {
        return comorbidade;
    }

    public void setComorbidade(Comorbidade comorbidade) {
        this.comorbidade = comorbidade;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
