package astral.astralbackend.entity;

import astral.astralbackend.dtos.compra.CadastroItemCompraDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "itemcompra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantidade;

    private BigDecimal precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    private Compra compra;

    public ItemCompra(Produto produto, Compra compra, Long quantidade) {
        this.produto = produto;
        this.compra = compra;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

}
