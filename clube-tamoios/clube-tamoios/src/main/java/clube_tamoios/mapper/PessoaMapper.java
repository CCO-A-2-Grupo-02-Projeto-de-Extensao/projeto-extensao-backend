package clube_tamoios.mapper;

import clube_tamoios.dto.response.PessoaResponse;
import clube_tamoios.entity.Cargo;
import clube_tamoios.entity.Pessoa;
import java.util.List;

public class PessoaMapper {

    public static PessoaResponse toResponse(Pessoa entity) {
        PessoaResponse dto = new PessoaResponse();
        dto.setIdPessoa(entity.getIdPessoa());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setRg(entity.getRg());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setTelefone(entity.getTelefone());
        dto.setIsDesbravador(entity.getDesbravador());
        dto.setAtivo(entity.getAtivo());
        dto.setEscola(entity.getEscola());
        dto.setSerieEscolar(entity.getSerieEscolar());
        dto.setNomeResponsavel1(entity.getNomeResponsavel1());
        dto.setTelefoneResponsavel1(entity.getTelefoneResponsavel1());
        dto.setRgResponsavel1(entity.getRgResponsavel1());
        dto.setCpfResponsavel1(entity.getCpfResponsavel1());
        dto.setNomeResponsavel2(entity.getNomeResponsavel2());
        dto.setTelefoneResponsavel2(entity.getTelefoneResponsavel2());
        dto.setRgResponsavel2(entity.getRgResponsavel2());
        dto.setCpfResponsavel2(entity.getCpfResponsavel2());

        if (entity.getClasse() != null) {
            dto.setNomeClasse(entity.getClasse().getNome());
            dto.setIdClasse(entity.getClasse().getIdClasse());
        }
        if (entity.getGenero() != null) {
            dto.setNomeGenero(entity.getGenero().getNome());
            dto.setIdGenero(entity.getGenero().getIdGenero());
        }
        if (entity.getUnidade() != null) {
            dto.setNomeUnidade(entity.getUnidade().getNome());
            dto.setIdUnidade(entity.getUnidade().getIdUnidade());
        }
        if (entity.getCargo() != null) {
            dto.setNomeCargo(entity.getCargo().getNome());
            dto.setIdCargo(entity.getCargo().getIdCargo());
        }
        dto.setCategoria(Cargo.categoriaDe(entity.getCargo()));

        return dto;
    }

    public static List<PessoaResponse> toResponse(List<Pessoa> entities) {
        return entities.stream().map(PessoaMapper::toResponse).toList();
    }
}
