package clube_tamoios.repository;

import clube_tamoios.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findBySerie(String serie);
    List<Evento> findByRepeticaoIsNotNullAndRepetirAteIsNull();
}