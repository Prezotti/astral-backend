package astral.astralbackend.entity;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Produtor extends Usuario {

    private String nome;

    private String telefone;

    private Boolean disponivel;

    private Boolean ativo;

    public Produtor(CadastroProdutorDTO dados) {
        super(dados.email(), dados.senha());
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.disponivel = true;
        this.ativo = true;
    }

    public void atualizarInformacoes(AtualizaProdutorDTO dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.email() != null){
            this.setEmail(dados.email());
        }
        if(dados.senha() != null){
            this.setSenha(dados.senha());
        }
    }

    public void excluir() {this.disponivel = false; this.ativo = false;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PRODUTOR"));
    }

}
