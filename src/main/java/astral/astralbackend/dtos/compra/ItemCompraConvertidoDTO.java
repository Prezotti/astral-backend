package astral.astralbackend.dtos.compra;

import astral.astralbackend.entity.Produto;
import jakarta.validation.constraints.NotNull;

public record ItemCompraConvertidoDTO(

        @NotNull Produto produto,

        @NotNull Long quantidade) {
}
