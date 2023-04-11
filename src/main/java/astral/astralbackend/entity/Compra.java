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
