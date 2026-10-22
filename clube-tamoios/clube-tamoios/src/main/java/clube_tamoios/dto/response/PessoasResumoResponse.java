package clube_tamoios.dto.response;

public record PessoasResumoResponse(
        long total,
        long ativos,
        long inativos,
        long administrativos,
        long instrutores,
        long alunos) {
}
