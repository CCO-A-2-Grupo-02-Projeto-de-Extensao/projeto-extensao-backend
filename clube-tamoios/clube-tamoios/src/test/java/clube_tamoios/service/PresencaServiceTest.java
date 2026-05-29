package clube_tamoios.service;

import clube_tamoios.dto.request.PresencaRequest;
import clube_tamoios.entity.Chamada;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Presenca;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ChamadaRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.PresencaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PresencaServiceTest {

    @Mock
    private PresencaRepository presencaRepository;

    @Mock
    private ChamadaRepository chamadaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PresencaService service;

    @Test
    @DisplayName("Deve registrar presença com sucesso")
    void deveRegistrarPresencaComSucesso() {
        PresencaRequest request = new PresencaRequest();
        request.setIdChamada(1);
        request.setIdPessoa(1);
        request.setPresente(true);

        Chamada chamada = new Chamada();
        chamada.setIdChamada(1);

        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);

        Presenca presencaSalva = new Presenca();
        presencaSalva.setId(1);
        presencaSalva.setChamada(chamada);
        presencaSalva.setPessoa(pessoa);
        presencaSalva.setPresente(true);

        when(chamadaRepository.findById(1)).thenReturn(Optional.of(chamada));
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(presencaRepository.save(any(Presenca.class))).thenReturn(presencaSalva);

        Presenca resultado = service.registrar(request);

        assertNotNull(resultado);
        assertTrue(resultado.getPresente());
        assertEquals(1, resultado.getChamada().getIdChamada());
        verify(presencaRepository, times(1)).save(any(Presenca.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar presença em chamada inexistente")
    void deveLancarExcecaoAoRegistrarPresencaChamadaInexistente() {
        PresencaRequest request = new PresencaRequest();
        request.setIdChamada(99);

        when(chamadaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.registrar(request));
    }
}