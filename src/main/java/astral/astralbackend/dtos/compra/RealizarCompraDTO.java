package astral.astralbackend.dtos.compra;

import astral.astralbackend.entity.ItemCompra;
import astral.astralbackend.enums.EFormaPagamento;
import astral.astralbackend.enums.EOpcaoRecebimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record RealizarCompraDTO(
        @NotBlank
        String cliente,

        @NotBlank
        String telefone,

        @NotBlank
        String endereco,

        List<CadastroItemCompraDTO> itens,

        EFormaPagamento formaPagamento,

        EOpcaoRecebimento opcaoRecebimento,

        BigDecimal doacao,

        String observacoes,

        @NotNull
        Long feiraId
) {
}
