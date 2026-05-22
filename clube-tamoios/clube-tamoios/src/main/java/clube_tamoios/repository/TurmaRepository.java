package clube_tamoios.repository;

import clube_tamoios.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    List<Turma> findByClasseIdClasse(Integer idClasse);
    List<Turma> findByUnidadeIdUnidade(Integer idUnidade);
}