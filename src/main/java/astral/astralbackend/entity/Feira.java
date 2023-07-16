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

    private BigDecimal totalEntregas;

    public Feira(CadastroFeiraDTO dados) {
        this.aberta = true;
        this.dataAbertura = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
        this.totalEntregas = BigDecimal.ZERO;
        this.taxaEntrega = dados.taxaEntrega();
    }

    public void habilitarDesabilitarFeira() {
        this.aberta = (!this.aberta);
    }

    public void adicionaValorCompra(BigDecimal valorCompra) {
        this.valorTotal = this.valorTotal.add(valorCompra);
    }

    public void adicionaValorEntrega() {
        this.totalEntregas = this.totalEntregas.add(this.taxaEntrega);
        this.valorTotal = this.valorTotal.add(this.taxaEntrega);
    }
}
