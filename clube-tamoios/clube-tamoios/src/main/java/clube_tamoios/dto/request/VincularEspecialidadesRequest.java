package clube_tamoios.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

// Vincular em lote: a tela de classe manda todas as especialidades marcadas no
// modal de uma vez.
public class VincularEspecialidadesRequest {

    @NotEmpty(message = "Informe ao menos uma especialidade")
    private List<Integer> idsEspecialidades;

    public List<Integer> getIdsEspecialidades() { return idsEspecialidades; }
    public void setIdsEspecialidades(List<Integer> idsEspecialidades) { this.idsEspecialidades = idsEspecialidades; }
}
