package clube_tamoios.mapper;

import clube_tamoios.dto.response.PresencaResponse;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Presenca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PresencaMapperTest {

    @Test
    @DisplayName("Deve converter Presenca (Entity) para PresencaResponse (DTO)")
    void deveConverterEntityParaDto() {
        Chamada chamada = new Chamada();
        chamada.setIdChamada(5);

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(15);
        pessoa.setNome("João Silva");

        Presenca presenca = new Presenca();
        presenca.setId(1);
        presenca.setChamada(chamada);
        presenca.setPessoa(pessoa);
        presenca.setPresente(true);

        PresencaResponse dto = PresencaMapper.toResponse(presenca);

        assertEquals(1, dto.getId());
        assertEquals(5, dto.getIdChamada());
        assertEquals(15, dto.getIdPessoa());
        assertEquals("João Silva", dto.getNomePessoa());
        assertTrue(dto.getPresente());
    }
}