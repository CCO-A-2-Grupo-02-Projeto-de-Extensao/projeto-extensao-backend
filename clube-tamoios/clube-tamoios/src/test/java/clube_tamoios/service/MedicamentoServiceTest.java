package clube_tamoios.service;

import clube_tamoios.dto.request.MedicamentoCadastroRequest;
import clube_tamoios.entity.Medicamento;
import clube_tamoios.exception.EntidadeNaoEncontradaException;
import clube_tamoios.repository.MedicamentoRepository;
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
class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoService service;

    private Medicamento medicamento;
    private MedicamentoCadastroRequest request;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento();
        medicamento.setId(1);
        medicamento.setNome("Ritalina");

        request = new MedicamentoCadastroRequest();
        request.setNome("Ritalina");
    }

    @Test
    void cadastrar_deveRetornarMedicamentoSalvo_quandoDadosValidos() {
        when(repository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento resultado = service.cadastrar(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Ritalina");
        verify(repository).save(any(Medicamento.class));
    }

    @Test
    void listar_deveRetornarTodosOsMedicamentos() {
        when(repository.findAll()).thenReturn(List.of(medicamento));

        List<Medicamento> resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Ritalina");
    }

    @Test
    void listar_deveRetornarListaVazia_quandoNaoHaMedicamentos() {
        when(repository.findAll()).thenReturn(List.of());

        List<Medicamento> resultado = service.listar();

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarMedicamento_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(medicamento));

        Medicamento resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Ritalina");
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void atualizar_deveAtualizarNome_quandoDadosValidos() {
        MedicamentoCadastroRequest novoRequest = new MedicamentoCadastroRequest();
        novoRequest.setNome("Concerta");

        when(repository.findById(1)).thenReturn(Optional.of(medicamento));
        when(repository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento resultado = service.atualizar(1, novoRequest);

        assertThat(resultado).isNotNull();
        verify(repository).save(medicamento);
    }

    @Test
    void atualizar_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, request))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deletar_deveDeletar_quandoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(medicamento));

        service.deletar(1);

        verify(repository).delete(medicamento);
    }

    @Test
    void deletar_deveLancarExcecao_quandoNaoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(EntidadeNaoEncontradaException.class);
    }
}
