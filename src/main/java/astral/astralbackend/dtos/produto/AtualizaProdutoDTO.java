package astral.astralbackend.dtos.produto;

import astral.astralbackend.enums.ECategoria;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AtualizaProdutoDTO(
        @NotNull
        Long id,
        String descricao,
        BigDecimal preco,
        Long qtdEstoque,
        String medida,
        ECategoria categoria
) {
}
