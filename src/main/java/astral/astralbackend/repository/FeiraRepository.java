package astral.astralbackend.repository;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDoProdutorDTO;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FeiraRepository extends JpaRepository<Feira, Long> {
    List<Feira> findAllByAbertaTrue();
    @Query("""
            SELECT f
            FROM Feira f
            ORDER BY f.dataAbertura DESC
            LIMIT 1
            """)
    Feira findMaisRecente();
}
