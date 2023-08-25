package astral.astralbackend.dtos.compra;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.enums.EFormaPagamento;
import astral.astralbackend.enums.EOpcaoRecebimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ListagemCompraDTO(
        Long id,
        LocalDateTime data,
        String cliente,
        String telefone,
        String endereco,
        String emailCliente,
        List<ListagemItemCompraDTO> itens,
        EFormaPagamento formaPagamento,
        EOpcaoRecebimento opcaoRecebimento,
        BigDecimal doacao,
        String observacoes,
        BigDecimal taxaEntrega,
        BigDecimal valorTotal
) {
    public ListagemCompraDTO(Compra compra) {
        this(compra.getId(),
                compra.getData(),
                compra.getCliente(),
                compra.getTelefone(),
                compra.getEndereco(),
                compra.getEmailCliente(),
                compra.getItens().stream()
                        .map(ListagemItemCompraDTO::new)
                        .collect(Collectors.toList()),
                compra.getFormaPagamento(),
                compra.getOpcaoRecebimento(),
                compra.getDoacao(),
                compra.getObservacoes(),
                compra.getTaxaEntrega(),
                compra.getValorTotal());
    }
}