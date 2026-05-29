package clube_tamoios.service;

import clube_tamoios.dto.request.TurmaRequest;
import clube_tamoios.entity.Classe;
import clube_tamoios.entity.Turma;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ClasseRepository;
import clube_tamoios.repository.TurmaRepository;
import clube_tamoios.repository.UnidadeRepository;
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
class TurmaServiceTest {

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private ClasseRepository classeRepository;

    @Mock
    private UnidadeRepository unidadeRepository;

    @InjectMocks
    private TurmaService service;

    @Test
    @DisplayName("Deve cadastrar turma (vincular classe e unidade) com sucesso")
    void deveCadastrarTurmaComSucesso() {
        TurmaRequest request = new TurmaRequest();
        request.setIdClasse(1);
        request.setIdUnidade(2);

        Classe classe = new Classe();
        classe.setIdClasse(1);

        Unidade unidade = new Unidade();
        unidade.setIdUnidade(2);

        Turma turmaSalva = new Turma();
        turmaSalva.setId(1);
        turmaSalva.setClasse(classe);
        turmaSalva.setUnidade(unidade);

        when(classeRepository.findById(1)).thenReturn(Optional.of(classe));
        when(unidadeRepository.findById(2)).thenReturn(Optional.of(unidade));
        when(turmaRepository.save(any(Turma.class))).thenReturn(turmaSalva);

        Turma resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(1, resultado.getClasse().getIdClasse());
        assertEquals(2, resultado.getUnidade().getIdUnidade());
        verify(turmaRepository, times(1)).save(any(Turma.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar turma com classe inexistente")
    void deveLancarExcecaoAoCadastrarTurmaClasseInexistente() {
        TurmaRequest request = new TurmaRequest();
        request.setIdClasse(99);
        request.setIdUnidade(1);

        when(classeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.cadastrar(request));
        verify(unidadeRepository, never()).findById(any());
        verify(turmaRepository, never()).save(any());
    }
}