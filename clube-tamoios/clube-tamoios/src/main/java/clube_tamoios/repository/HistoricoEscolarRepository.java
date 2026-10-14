package clube_tamoios.repository;

import clube_tamoios.entity.HistoricoEscolar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoEscolarRepository extends JpaRepository<HistoricoEscolar, Integer> {

    List<HistoricoEscolar> findByPessoaIdPessoaOrderByAnoDesc(Integer idPessoa);

    boolean existsByPessoaIdPessoaAndAno(Integer idPessoa, Integer ano);
}
