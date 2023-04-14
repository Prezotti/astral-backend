package astral.astralbackend.entity;

import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.enums.ECategoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal preco;

    private Long qtdEstoque;

    private String medida;

    private String imagem;

    @Enumerated(EnumType.STRING)
    private ECategoria categoria;

    private Boolean disponivel;

    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;

    public Produto(CadastroProdutoDTO dados) {

    }
}
