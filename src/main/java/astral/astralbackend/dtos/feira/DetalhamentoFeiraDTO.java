package astral.astralbackend.dtos.feira;

import astral.astralbackend.entity.Feira;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalhamentoFeiraDTO(Long id, Boolean aberta, LocalDateTime dataAbertura, BigDecimal taxaEntrega, BigDecimal valorTotal) {
    public DetalhamentoFeiraDTO(Feira feira) {
        this(feira.getId(), feira.getAberta(), feira.getDataAbertura(), feira.getTaxaEntrega(), feira.getValorTotal());
    }
}
