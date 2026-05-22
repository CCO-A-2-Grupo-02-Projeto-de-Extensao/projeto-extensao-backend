package clube_tamoios.service;

import clube_tamoios.dto.request.ComorbidadeCadastroRequest;
import clube_tamoios.entity.Comorbidade;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.ComorbidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComorbidadeServiceTest {

    @Mock
    private ComorbidadeRepository repository;

    @InjectMocks
    private ComorbidadeService service;

    private Comorbidade comorbidade;
    private ComorbidadeCadastroRequest request;

    @BeforeEach
    void setUp() {
        comorbidade = new Comorbidade();
        comorbidade.setId(1);
        comorbidade.setNome("Diabetes");

        request = new ComorbidadeCadastroRequest();
        request.setNome("Diabetes");
    }

    @Test
    void cadastrar_deveRetornarComorbidadeSalva_quandoDadosValidos() {
        when(repository.save(any(Comorbidade.class))).thenReturn(comorbidade);

        Comorbidade resultado = service.cadastrar(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Diabetes");
        verify(repository).save(any(Comorbidade.class));
    }

    @Test
    void listar_deveRetornarTodasAsComorbidades() {
        when(repository.findAll()).thenReturn(List.of(comorbidade));

        List<Comorbidade> resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Diabetes");
    }

    @Test
    void listar_deveRetornarListaVazia_quandoNaoHaComorbidades() {
        when(repository.findAll()).thenReturn(List.of());

        List<Comorbidade> resultado = service.listar();

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarComorbidade_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(comorbidade));

        Comorbidade resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Diabetes");
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrada() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveAtualizarNome_quandoDadosValidos() {
        ComorbidadeCadastroRequest novoRequest = new ComorbidadeCadastroRequest();
        novoRequest.setNome("Hipertensão");

        when(repository.findById(1)).thenReturn(Optional.of(comorbidade));
        when(repository.save(any(Comorbidade.class))).thenReturn(comorbidade);

        Comorbidade resultado = service.atualizar(1, novoRequest);

        assertThat(resultado).isNotNull();
        verify(repository).save(comorbidade);
    }

    @Test
    void atualizar_deveLancarExcecao_quandoNaoEncontrada() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, request))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(comorbidade));

        service.deletar(1);

        verify(repository).delete(comorbidade);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrada() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
