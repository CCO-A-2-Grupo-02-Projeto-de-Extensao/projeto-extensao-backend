package clube_tamoios.mapper;

import clube_tamoios.dto.response.TurmaResponse;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Turma;
import clube_tamoios.entity.Unidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TurmaMapperTest {

    @Test
    @DisplayName("Deve converter Turma (Entity) para TurmaResponse (DTO)")
    void deveConverterEntityParaDto() {
        // Cenário
        Classe classe = new Classe();
        classe.setIdClasse(10);
        classe.setNome("Amigo");

        Unidade unidade = new Unidade();
        unidade.setIdUnidade(20);
        unidade.setNome("Leões");

        Turma turma = new Turma();
        turma.setId(1);
        turma.setClasse(classe);
        turma.setUnidade(unidade);

        // Ação
        TurmaResponse dto = TurmaMapper.toResponse(turma);

        // Verificação
        assertEquals(1, dto.getId());
        assertEquals(10, dto.getIdClasse());
        assertEquals("Amigo", dto.getNomeClasse());
        assertEquals(20, dto.getIdUnidade());
        assertEquals("Leões", dto.getNomeUnidade());
    }

    @Test
    @DisplayName("Não deve quebrar se Classe ou Unidade forem nulos")
    void deveLidarComRelacionamentosNulos() {
        Turma turma = new Turma();
        turma.setId(1);

        TurmaResponse dto = TurmaMapper.toResponse(turma);

        assertEquals(1, dto.getId());
        assertNull(dto.getIdClasse());
        assertNull(dto.getIdUnidade());
    }
}