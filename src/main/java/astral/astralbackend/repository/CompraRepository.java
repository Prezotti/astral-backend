package astral.astralbackend.repository;

import astral.astralbackend.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findAllByFeiraId(Long id);
}
