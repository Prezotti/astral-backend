package astral.astralbackend.dtos.produto;

import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.entity.Produto;
import astral.astralbackend.enums.ECategoria;

import java.math.BigDecimal;

public record DetalhamentoProdutoDTO(Long produtoId,
                                     String descricao,
                                     BigDecimal preco,
                                     Long qtdEstoque,
                                     String medida,
                                     String imagem,
                                     ECategoria categoria,
                                     boolean disponivel,
                                     DetalhamentoProdutorDTO produtor) {
    public DetalhamentoProdutoDTO(Produto produto) {
        this(produto.getId(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQtdEstoque(),
                produto.getMedida(),
                produto.getImagem(),
                produto.getCategoria(),
                produto.getDisponivel(),
                new DetalhamentoProdutorDTO(produto.getProdutor()));
    }
}