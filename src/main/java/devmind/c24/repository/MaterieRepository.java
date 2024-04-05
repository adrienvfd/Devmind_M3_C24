package devmind.c24.repository;

import devmind.c24.model.Materie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterieRepository extends JpaRepository<Materie, Integer> {

}