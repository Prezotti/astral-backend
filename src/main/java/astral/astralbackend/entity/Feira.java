package astral.astralbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Feira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean aberta;

    private LocalDateTime dataAbertura;
    
    private BigDecimal valorTotal;

    public Feira() {
        this.aberta = true;
        this.dataAbertura = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
    }

    public void habilitarDesabilitarFeira() {
        this.aberta = false;
    }
}
