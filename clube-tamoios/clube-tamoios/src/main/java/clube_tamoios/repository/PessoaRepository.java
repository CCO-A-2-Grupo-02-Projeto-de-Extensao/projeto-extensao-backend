package clube_tamoios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import clube_tamoios.entity.Pessoa;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    List<Pessoa> findByClasseIdClasseAndAtivoTrue(Integer idClasse);

    long countByUnidadeIdUnidadeAndClasseIdClasseAndAtivoTrue(Integer idUnidade, Integer idClasse);
}
