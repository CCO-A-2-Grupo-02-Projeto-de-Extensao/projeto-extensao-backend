package clube_tamoios.service;

import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Pessoa;
import clube_tamoios.entity.Unidade;
import clube_tamoios.exception.RegraDeNegocioException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegrasUnidade {

    public static final int MINIMO_DESBRAVADORES = 4;
    public static final int MAXIMO_DESBRAVADORES = 8;

    private static final Pattern NUMERO = Pattern.compile("\\d+");

    private RegrasUnidade() {
    }

    public static Integer idadeEm(String dataNascimento) {
        if (dataNascimento == null || dataNascimento.isBlank()) {
            return null;
        }
        try {
            return Period.between(LocalDate.parse(dataNascimento.trim()), LocalDate.now()).getYears();
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static int[] faixaEtaria(String faixa) {
        if (faixa == null) {
            return null;
        }

        List<Integer> numeros = new ArrayList<>();
        Matcher matcher = NUMERO.matcher(faixa);
        while (matcher.find()) {
            numeros.add(Integer.parseInt(matcher.group()));
        }

        if (numeros.size() < 2) {
            return null;
        }

        int primeiro = numeros.get(0);
        int segundo = numeros.get(1);
        return new int[] { Math.min(primeiro, segundo), Math.max(primeiro, segundo) };
    }

    public static void validarDadosDaUnidade(Integer idGenero, String faixaEtaria) {
        if (idGenero == null) {
            throw new RegraDeNegocioException(
                    "Informe o sexo da unidade: as unidades são separadas por sexo.");
        }
        if (faixaEtaria(faixaEtaria) == null) {
            throw new RegraDeNegocioException(
                    "Informe a faixa etária da unidade no formato \"10 - 11\": as unidades são separadas por idade.");
        }
    }

    public static boolean contaComoDesbravador(Pessoa pessoa) {
        return "aluno".equals(Cargo.categoriaDe(pessoa.getCargo()));
    }

    private static String problemaDeSexo(Pessoa pessoa, Unidade unidade) {
        if (unidade.getGenero() == null || pessoa.getGenero() == null
                || unidade.getGenero().getIdGenero().equals(pessoa.getGenero().getIdGenero())) {
            return null;
        }
        return pessoa.getNome() + " é " + pessoa.getGenero().getNome()
                + " e a unidade " + unidade.getNome() + " é " + unidade.getGenero().getNome() + ".";
    }

    private static String problemaDeIdade(Pessoa pessoa, Unidade unidade) {
        int[] faixa = faixaEtaria(unidade.getFaixaEtaria());
        Integer idade = idadeEm(pessoa.getDataNascimento());

        if (faixa == null || idade == null || (idade >= faixa[0] && idade <= faixa[1])) {
            return null;
        }
        return pessoa.getNome() + " tem " + idade + " anos e a unidade " + unidade.getNome()
                + " é da faixa de " + faixa[0] + " a " + faixa[1] + " anos.";
    }

    public static void validarEntrada(Pessoa pessoa, Unidade unidade, long ocupacaoAtual) {
        if (!contaComoDesbravador(pessoa)) {
            return;
        }

        if (ocupacaoAtual >= MAXIMO_DESBRAVADORES) {
            throw new RegraDeNegocioException("A unidade " + unidade.getNome() + " já tem "
                    + MAXIMO_DESBRAVADORES + " desbravadores, o máximo permitido.");
        }

        String sexo = problemaDeSexo(pessoa, unidade);
        if (sexo != null) {
            throw new RegraDeNegocioException(sexo);
        }

        String idade = problemaDeIdade(pessoa, unidade);
        if (idade != null) {
            throw new RegraDeNegocioException(idade);
        }
    }

    public static List<String> inconsistencias(Unidade unidade, List<Pessoa> desbravadores) {
        List<String> problemas = new ArrayList<>();

        if (unidade.getGenero() == null) {
            problemas.add("A unidade não tem sexo definido.");
        }
        if (faixaEtaria(unidade.getFaixaEtaria()) == null) {
            problemas.add("A unidade não tem faixa etária definida.");
        }

        int total = desbravadores.size();
        if (total > MAXIMO_DESBRAVADORES) {
            problemas.add("Tem " + total + " desbravadores; o máximo é " + MAXIMO_DESBRAVADORES + ".");
        } else if (total < MINIMO_DESBRAVADORES) {
            problemas.add("Tem " + total + " desbravadores; o mínimo é " + MINIMO_DESBRAVADORES + ".");
        }

        for (Pessoa pessoa : desbravadores) {
            String sexo = problemaDeSexo(pessoa, unidade);
            if (sexo != null) {
                problemas.add(sexo);
            }

            String idade = problemaDeIdade(pessoa, unidade);
            if (idade != null) {
                problemas.add(idade);
            }
        }

        return problemas;
    }
}
