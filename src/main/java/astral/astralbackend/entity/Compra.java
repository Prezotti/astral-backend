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

    private String emailCliente;

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

    private BigDecimal taxaEntrega = BigDecimal.ZERO;

    public Compra(String cliente, String telefone, String endereco, String emailCliente, EFormaPagamento formaPagamento, EOpcaoRecebimento opcaoRecebimento, BigDecimal doacao, String observacoes, Feira feira) {
        this.data = LocalDateTime.now();
        this.cliente = cliente;
        this.telefone = telefone;
        this.endereco = endereco;
        this.emailCliente = emailCliente;
        this.formaPagamento = formaPagamento;
        this.opcaoRecebimento = opcaoRecebimento;
        this.doacao = doacao;
        this.observacoes = observacoes;
        this.feira = feira;
    }

    public void adicionarItem(ItemCompra item) {
        item.setCompra(this);
        this.itens.add(item);
    }

    public BigDecimal calculaValorProdutos(){
        BigDecimal valorProdutos = BigDecimal.ZERO;
        for (ItemCompra item : this.itens) {
            valorProdutos = valorProdutos.add(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
        }
        return valorProdutos;
    }

    public void calculaValorTotal() {
        this.valorTotal = this.valorTotal.add(this.calculaValorProdutos());

        if (this.opcaoRecebimento.equals(EOpcaoRecebimento.ENTREGA)) {
            this.valorTotal = this.valorTotal.add(this.feira.getTaxaEntrega());
            this.taxaEntrega = this.feira.getTaxaEntrega();
        }

        this.valorTotal = this.valorTotal.add(this.doacao);
    }

    public void setItens(List<ItemCompra> itens) {
        this.itens = itens;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
