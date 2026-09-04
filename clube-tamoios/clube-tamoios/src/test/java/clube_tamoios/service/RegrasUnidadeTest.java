package clube_tamoios.service;

import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Genero;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.RegraDeNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegrasUnidadeTest {

    private static Genero genero(int id, String nome) {
        Genero genero = new Genero();
        genero.setIdGenero(id);
        genero.setNome(nome);
        return genero;
    }

    private static Cargo cargo(String nome) {
        Cargo cargo = new Cargo();
        cargo.setNome(nome);
        return cargo;
    }

    private static Unidade unidade(String nome, Genero genero, String faixaEtaria) {
        Unidade unidade = new Unidade();
        unidade.setIdUnidade(1);
        unidade.setNome(nome);
        unidade.setGenero(genero);
        unidade.setFaixaEtaria(faixaEtaria);
        return unidade;
    }

    private static Pessoa desbravador(String nome, Genero genero, int idade) {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNome(nome);
        pessoa.setGenero(genero);
        pessoa.setCargo(cargo("Desbravador"));
        pessoa.setDataNascimento(LocalDate.now().minusYears(idade).toString());
        return pessoa;
    }

    @Test
    @DisplayName("Deve aceitar desbravador do mesmo sexo e dentro da faixa etária")
    void deveAceitarDesbravadorCompativel() {
        Genero masculino = genero(1, "Masculino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        assertDoesNotThrow(() ->
                RegrasUnidade.validarEntrada(desbravador("João", masculino, 10), unidade, 3));
    }

    @Test
    @DisplayName("Deve recusar desbravador quando a unidade já tem o máximo de 8")
    void deveRecusarUnidadeLotada() {
        Genero masculino = genero(1, "Masculino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        RegraDeNegocioException erro = assertThrows(RegraDeNegocioException.class, () ->
                RegrasUnidade.validarEntrada(desbravador("João", masculino, 10), unidade,
                        RegrasUnidade.MAXIMO_DESBRAVADORES));

        assertTrue(erro.getMessage().contains("máximo permitido"));
    }

    @Test
    @DisplayName("Deve recusar desbravador de sexo diferente do da unidade")
    void deveRecusarSexoDiferente() {
        Genero masculino = genero(1, "Masculino");
        Genero feminino = genero(2, "Feminino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        assertThrows(RegraDeNegocioException.class, () ->
                RegrasUnidade.validarEntrada(desbravador("Ana", feminino, 10), unidade, 0));
    }

    @Test
    @DisplayName("Deve recusar desbravador fora da faixa etária da unidade")
    void deveRecusarForaDaFaixaEtaria() {
        Genero masculino = genero(1, "Masculino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        assertThrows(RegraDeNegocioException.class, () ->
                RegrasUnidade.validarEntrada(desbravador("Pedro", masculino, 14), unidade, 0));
    }

    @Test
    @DisplayName("Não deve aplicar as regras da unidade a quem não é desbravador")
    void naoDeveValidarConselheiro() {
        Genero masculino = genero(1, "Masculino");
        Genero feminino = genero(2, "Feminino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        Pessoa conselheira = desbravador("Fernanda", feminino, 36);
        conselheira.setCargo(cargo("Instrutor"));

        assertDoesNotThrow(() ->
                RegrasUnidade.validarEntrada(conselheira, unidade, RegrasUnidade.MAXIMO_DESBRAVADORES));
    }

    @Test
    @DisplayName("Deve apontar a menina numa unidade masculina e o desbravador fora da faixa")
    void deveApontarInconsistenciasDaUnidade() {
        Genero masculino = genero(1, "Masculino");
        Genero feminino = genero(2, "Feminino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        List<String> problemas = RegrasUnidade.inconsistencias(unidade, List.of(
                desbravador("João", masculino, 10),
                desbravador("Ana", feminino, 10),
                desbravador("Pedro", masculino, 14),
                desbravador("Lucas", masculino, 11)));

        assertTrue(problemas.stream().anyMatch(p -> p.contains("Ana é Feminino")));
        assertTrue(problemas.stream().anyMatch(p -> p.contains("Pedro tem 14 anos")));
        assertEquals(2, problemas.size());
    }

    @Test
    @DisplayName("Deve apontar unidade abaixo do mínimo e sem sexo nem faixa etária")
    void deveApontarUnidadeIncompleta() {
        List<String> problemas = RegrasUnidade.inconsistencias(unidade("Nova", null, null), List.of());

        assertTrue(problemas.stream().anyMatch(p -> p.contains("sexo")));
        assertTrue(problemas.stream().anyMatch(p -> p.contains("faixa etária")));
        assertTrue(problemas.stream().anyMatch(p -> p.contains("mínimo é 4")));
    }

    @Test
    @DisplayName("Não deve apontar nada numa unidade consistente")
    void naoDeveApontarUnidadeConsistente() {
        Genero masculino = genero(1, "Masculino");
        Unidade unidade = unidade("Jacutinga", masculino, "10 - 11");

        assertTrue(RegrasUnidade.inconsistencias(unidade, List.of(
                desbravador("João", masculino, 10),
                desbravador("Lucas", masculino, 11),
                desbravador("Tiago", masculino, 10),
                desbravador("Bruno", masculino, 11))).isEmpty());
    }

    @Test
    @DisplayName("Deve exigir sexo e faixa etária ao cadastrar a unidade")
    void deveExigirSexoEFaixaEtaria() {
        assertThrows(RegraDeNegocioException.class,
                () -> RegrasUnidade.validarDadosDaUnidade(null, "10 - 11"));

        assertThrows(RegraDeNegocioException.class,
                () -> RegrasUnidade.validarDadosDaUnidade(1, ">15"));

        assertDoesNotThrow(() -> RegrasUnidade.validarDadosDaUnidade(1, "10 - 11"));
    }
}
