package astral.astralbackend.dtos.feira;

import astral.astralbackend.entity.Feira;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalhamentoFeiraAbertaDTO(Long id, Boolean aberta, LocalDateTime dataAbertura, BigDecimal taxaEntrega) {
    public DetalhamentoFeiraAbertaDTO(Feira feira) {
        this(feira.getId(), feira.getAberta(), feira.getDataAbertura(), feira.getTaxaEntrega());
    }
}
