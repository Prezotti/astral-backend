package astral.astralbackend.repository;

import astral.astralbackend.entity.Feira;
import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeiraRepository extends JpaRepository<Feira, Long> {
    @Query("SELECT f FROM Feira f WHERE f.aberta = true")
    List<Feira> findAllAbertas();
}
