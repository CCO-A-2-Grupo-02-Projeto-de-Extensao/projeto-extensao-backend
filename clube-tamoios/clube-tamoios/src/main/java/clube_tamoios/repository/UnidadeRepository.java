package clube_tamoios.repository;

import clube_tamoios.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
}
