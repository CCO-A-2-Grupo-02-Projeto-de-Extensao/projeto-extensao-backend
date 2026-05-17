package clube_tamoios.repository;

import clube_tamoios.entity.FichaMedica;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaMedicaRepository extends JpaRepository<FichaMedica, Integer> {

    Optional<FichaMedica> findByPessoaIdPessoa(Integer idPessoa);

    boolean existsByPessoaIdPessoa(Integer idPessoa);
}
