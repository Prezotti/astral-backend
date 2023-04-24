package astral.astralbackend.repository;

import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Produtor, Long> {

    UserDetails findByEmail(String email);

}
