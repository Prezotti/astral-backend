package astral.astralbackend.entity;

import astral.astralbackend.dtos.compra.ItemCompraConvertidoDTO;
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

    @Enumerated(EnumType.STRING)
    private EFormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private EOpcaoRecebimento opcaoRecebimento;

    private BigDecimal doacao;

    private String observacoes;

    private BigDecimal valorTotal = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feira feira;

    public Compra(String cliente, String telefone, String endereco, List<ItemCompraConvertidoDTO> itens, EFormaPagamento formaPagamento, EOpcaoRecebimento opcaoRecebimento, BigDecimal doacao, String observacoes, Feira feira) {
        this.data = LocalDateTime.now();
        this.cliente = cliente;
        this.telefone = telefone;
        this.endereco = endereco;
        this.adicionarItens(itens);
        this.formaPagamento = formaPagamento;
        this.opcaoRecebimento = opcaoRecebimento;
        this.doacao = doacao;
        this.observacoes = observacoes;
        this.feira = feira;
        this.calculaValorTotal();

    }

    private void adicionarItens(List<ItemCompraConvertidoDTO> itens) {
        for (ItemCompraConvertidoDTO item : itens) {
            ItemCompra itemCompra = new ItemCompra(item.produto(), this, item.quantidade());
            this.adicionarItem(itemCompra);
        }
    }

    private void adicionarItem(ItemCompra item) {
        item.setCompra(this);
        this.itens.add(item);
    }

    private void calculaValorTotal() {
        for (ItemCompra item : this.itens){
            this.valorTotal = this.valorTotal.add(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
        }
        if (this.opcaoRecebimento.equals(EOpcaoRecebimento.ENTREGA)){
            this.valorTotal.add(this.feira.getTaxaEntrega());
        }
    }
}
