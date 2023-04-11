package astral.astralbackend.entity;

import astral.astralbackend.enums.EFormaPagamento;
import astral.astralbackend.enums.EOpcaoRecebimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    private String cliente;

    private String telefone;

    private String endereco;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemCompra> itens = new ArrayList<>();

    public void adicionarItem(ItemCompra item){
        item.setCompra(this);
        this.itens.add(item);
    }

    @Enumerated(EnumType.STRING)
    private EFormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private EOpcaoRecebimento opcaoRecebimento;

    private BigDecimal doacao;

    private String observacoes;

    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feira feira;

}
