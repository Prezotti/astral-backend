package astral.astralbackend.dtos.compra;

import astral.astralbackend.entity.ItemCompra;

public record ListagemItemCompraDTO(
        String produto,
        String produtor,
        Long quantidade
) {
    public ListagemItemCompraDTO(ItemCompra itemCompra) {
        this(itemCompra.getProduto().getDescricao(),
                itemCompra.getProduto().getProdutor().getNome(),
                itemCompra.getQuantidade());
    }
}
