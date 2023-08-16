package astral.astralbackend.dtos.feira;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalhamentoFeiraDoProdutorDTO(
        Long id,
        Boolean aberta,
        LocalDateTime dataAbertura,
        BigDecimal valorTotal) {

}
