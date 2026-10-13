package clube_tamoios.repository;

import clube_tamoios.entity.EspecialidadeConcluida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeConcluidaRepository extends JpaRepository<EspecialidadeConcluida, Integer> {

    List<EspecialidadeConcluida> findByPessoaIdPessoa(Integer idPessoa);

    boolean existsByPessoaIdPessoaAndEspecialidadeIdEspecialidade(Integer idPessoa, Integer idEspecialidade);
}
