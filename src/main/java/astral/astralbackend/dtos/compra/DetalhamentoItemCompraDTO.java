package astral.astralbackend.dtos.compra;

import astral.astralbackend.dtos.produto.DetalhamentoProdutoDTO;
import astral.astralbackend.entity.ItemCompra;

public record DetalhamentoItemCompraDTO(
        DetalhamentoProdutoDTO produto,
        Long quantidade
) {
    public DetalhamentoItemCompraDTO(ItemCompra itemCompra){
        this(new DetalhamentoProdutoDTO(itemCompra.getProduto()),
        itemCompra.getQuantidade());
    }
}
