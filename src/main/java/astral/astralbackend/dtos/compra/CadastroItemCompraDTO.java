package astral.astralbackend.dtos.compra;

import jakarta.validation.constraints.NotNull;

public record CadastroItemCompraDTO(

        @NotNull
        Long produtoId,

        @NotNull
        Long quantidade
) {
}
