package clube_tamoios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import clube_tamoios.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
