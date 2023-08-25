package astral.astralbackend.dtos.compra;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.enums.EFormaPagamento;
import astral.astralbackend.enums.EOpcaoRecebimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DetalhamentoEntregaDTO(
        Long id,
        String cliente,
        String telefone,
        String endereco,
        String emailCliente,
        EFormaPagamento formaPagamento,
        BigDecimal doacao,
        BigDecimal valorTotal
) {
    public DetalhamentoEntregaDTO(Compra compra) {
        this(compra.getId(),
        compra.getCliente(),
        compra.getTelefone(),
        compra.getEndereco(),
        compra.getEmailCliente(),
        compra.getFormaPagamento(),
        compra.getDoacao(),
        compra.getValorTotal());
    }
}
