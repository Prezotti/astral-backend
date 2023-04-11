package astral.astralbackend.repository;

import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeiraRepository extends JpaRepository<Produtor, Long> {

}
