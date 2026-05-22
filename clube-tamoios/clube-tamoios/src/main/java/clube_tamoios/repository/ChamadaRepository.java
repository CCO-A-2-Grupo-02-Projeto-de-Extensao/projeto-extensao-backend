package clube_tamoios.repository;

import clube_tamoios.entity.Chamada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChamadaRepository extends JpaRepository<Chamada, Integer> {
    List<Chamada> findByEventoIdEvento(Integer idEvento);
}