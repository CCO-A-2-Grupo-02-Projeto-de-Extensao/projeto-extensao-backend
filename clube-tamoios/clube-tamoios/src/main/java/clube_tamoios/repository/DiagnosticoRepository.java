package clube_tamoios.repository;

import clube_tamoios.entity.Diagnostico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Integer> {

    List<Diagnostico> findByFichaMedicaId(Integer idFichaMedica);
}
