package clube_tamoios.repository;

import clube_tamoios.entity.Chamada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChamadaRepository extends JpaRepository<Chamada, Integer> {
    List<Chamada> findByEventoIdEvento(Integer idEvento);
    Optional<Chamada> findByDataChamada(LocalDate dataChamada);
}