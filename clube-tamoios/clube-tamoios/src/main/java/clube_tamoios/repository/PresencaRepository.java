package clube_tamoios.repository;

import clube_tamoios.entity.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PresencaRepository extends JpaRepository<Presenca, Integer> {
    List<Presenca> findByChamadaIdChamada(Integer idChamada);
}