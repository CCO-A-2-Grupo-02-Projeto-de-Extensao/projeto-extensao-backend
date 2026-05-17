package clube_tamoios.repository;

import clube_tamoios.entity.Documento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    List<Documento> findByPessoaIdPessoa(Integer idPessoa);
}
