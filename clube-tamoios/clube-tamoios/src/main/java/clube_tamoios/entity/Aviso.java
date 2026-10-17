package clube_tamoios.entity;

import java.time.LocalDate;

public record Aviso(
        int id,
        String nome,
        String descricao,
        LocalDate dataCriacao
) {}