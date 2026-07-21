package clube_tamoios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import clube_tamoios.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
}
