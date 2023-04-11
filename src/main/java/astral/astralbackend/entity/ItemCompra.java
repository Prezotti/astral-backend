package astral.astralbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;

    private BigDecimal precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    private Compra compra;

    public ItemCompra(Produto produto, Compra compra, int quantidade) {
        this.produto = produto;
        this.compra = compra;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }
}
