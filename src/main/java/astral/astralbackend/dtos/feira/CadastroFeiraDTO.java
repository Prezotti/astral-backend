package astral.astralbackend.dtos.feira;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastroFeiraDTO(
        BigDecimal valorTotal) {
}
