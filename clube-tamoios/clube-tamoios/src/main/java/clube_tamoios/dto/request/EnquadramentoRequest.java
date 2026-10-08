package clube_tamoios.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Enquadramento de uma imagem exibida recortada (hoje a foto de perfil).
 * posX/posY são percentuais de object-position: 0 encosta a imagem no início
 * do eixo, 100 no fim e 50 centraliza. zoom é o fator de escala.
 */
public class EnquadramentoRequest {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double posX;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double posY;

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("4.0")
    private Double zoom;

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public Double getZoom() {
        return zoom;
    }

    public void setZoom(Double zoom) {
        this.zoom = zoom;
    }
}
