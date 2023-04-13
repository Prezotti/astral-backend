package astral.astralbackend.repository;

import astral.astralbackend.entity.Produtor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    UserDetails findByEmail(String email);

    Page<Produtor> findAllByAtivoTrue(Pageable page);

    Page<Produtor> findByAtivoTrueAndDisponivel(Pageable pageable, boolean disponivel);

}
