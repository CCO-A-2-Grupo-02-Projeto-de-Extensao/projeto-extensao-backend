package clube_tamoios.repository;

import clube_tamoios.entity.Ocorrencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

    List<Ocorrencia> findByPessoaIdPessoa(Integer idPessoa);
}
