package astral.astralbackend.repository;

import astral.astralbackend.entity.Produtor;
import astral.astralbackend.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    UserDetails findByEmail(String email);

    List<Produtor> findAllByAtivoTrue();

    List<Produtor> findByAtivoTrueAndDisponivel(boolean disponivel);

}
