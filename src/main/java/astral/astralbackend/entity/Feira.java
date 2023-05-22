package astral.astralbackend.entity;

import astral.astralbackend.dtos.feira.CadastroFeiraDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Feira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean aberta;

    private LocalDateTime dataAbertura;

    private BigDecimal taxaEntrega;

    private BigDecimal valorTotal;

    public Feira(CadastroFeiraDTO dados) {
        this.aberta = true;
        this.dataAbertura = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
        this.taxaEntrega = dados.taxaEntrega();
    }

    public Feira(Boolean aberta, BigDecimal taxaEntrega) {
        this.aberta = aberta;
        this.taxaEntrega = taxaEntrega;
        this.dataAbertura = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
    }

    public void habilitarDesabilitarFeira() {
        this.aberta = (!this.aberta);
    }

    public void adicionaValorCompra(BigDecimal valorCompra) {
        this.valorTotal = this.valorTotal.add(valorCompra);
    }
}
