package clube_tamoios.repository;

import clube_tamoios.entity.Medicacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicacaoRepository extends JpaRepository<Medicacao, Integer> {

    List<Medicacao> findByFichaMedicaId(Integer idFichaMedica);
}
