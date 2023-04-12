package astral.astralbackend.repository;

import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    UserDetails findByEmail(String email);
}
