package astral.astralbackend.dtos.feira;

import astral.astralbackend.entity.Feira;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalhamentoFeiraRecenteDTO(Long id, LocalDateTime dataAbertura) {
    public DetalhamentoFeiraRecenteDTO(Feira feira) {
        this(feira.getId(), feira.getDataAbertura());
    }
}
