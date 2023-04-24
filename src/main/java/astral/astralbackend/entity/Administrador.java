package astral.astralbackend.entity;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PRODUTOR"), new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
