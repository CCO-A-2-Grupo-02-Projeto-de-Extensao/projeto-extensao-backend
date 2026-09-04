package clube_tamoios.service;

import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Turma;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.GeneroRepository;
import clube_tamoios.repository.PessoaRepository;
import clube_tamoios.repository.TurmaRepository;
import clube_tamoios.repository.UnidadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnidadeServiceTest {

    @Mock
    private UnidadeRepository unidadeRepository;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TurmaRepository turmaRepository;

    @InjectMocks
    private UnidadeService service;

    private static Unidade unidade(Integer id) {
        Unidade unidade = new Unidade();
        unidade.setIdUnidade(id);
        unidade.setNome("Falcões");
        return unidade;
    }

    private static Pessoa pessoa(Integer id, Unidade unidade) {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(id);
        pessoa.setNome("Desbravador " + id);
        pessoa.setUnidade(unidade);
        return pessoa;
    }

    @Test
    @DisplayName("Deve desvincular os desbravadores e apagar as turmas ao excluir a unidade")
    void deveDesvincularAoExcluir() {
        Unidade alvo = unidade(1);
        Pessoa dentro = pessoa(10, alvo);
        Pessoa fora = pessoa(11, unidade(2));
        Turma turma = new Turma();

        when(unidadeRepository.findById(1)).thenReturn(Optional.of(alvo));
        when(pessoaRepository.findAll()).thenReturn(List.of(dentro, fora));
        when(turmaRepository.findByUnidadeIdUnidade(1)).thenReturn(List.of(turma));

        service.deletar(1);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Pessoa>> salvas = ArgumentCaptor.forClass(List.class);
        verify(pessoaRepository).saveAll(salvas.capture());

        assertEquals(List.of(dentro), salvas.getValue());
        assertNull(dentro.getUnidade());
        assertNotNull(fora.getUnidade());

        verify(turmaRepository).deleteAll(List.of(turma));
        verify(unidadeRepository).delete(alvo);
        verify(pessoaRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve falhar ao excluir unidade inexistente")
    void deveFalharComUnidadeInexistente() {
        when(unidadeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.deletar(99));

        verify(unidadeRepository, never()).delete(any());
    }
}
