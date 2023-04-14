package astral.astralbackend.entity;

import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private String senha;

    private Boolean disponivel;

    private Boolean ativo;

    public Produtor(CadastroProdutorDTO dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.senha = new BCryptPasswordEncoder().encode(dados.senha());
        this.disponivel = true;
        this.ativo = true;
    }
}
