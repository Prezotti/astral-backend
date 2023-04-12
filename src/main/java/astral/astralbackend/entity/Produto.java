package astral.astralbackend.entity;

import astral.astralbackend.enums.ECategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal preco;

    private int qtdEstoque;

    private String medida;

    private String imagem;

    @Enumerated(EnumType.STRING)
    private ECategoria categoria;

    private Boolean disponivel;

    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;

}
