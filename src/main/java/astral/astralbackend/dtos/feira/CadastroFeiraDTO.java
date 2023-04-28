package astral.astralbackend.dtos.feira;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CadastroFeiraDTO(
        @NotNull
        BigDecimal taxaEntrega
) {
}
