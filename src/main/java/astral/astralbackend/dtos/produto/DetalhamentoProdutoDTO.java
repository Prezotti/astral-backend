package astral.astralbackend.dtos.produto;

import astral.astralbackend.entity.Produto;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.enums.ECategoria;

import java.math.BigDecimal;

public record DetalhamentoProdutoDTO(Long produtoId, String descricao, BigDecimal preco, Long qtdEstoque, String medida, String imagem, ECategoria categoria, Long produtorId, String nomeProdutor){
    public DetalhamentoProdutoDTO(Produto produto){
        this(produto.getId(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque(), produto.getMedida(), produto.getImagem(), produto.getCategoria(), produto.getProdutor().getId(), produto.getProdutor().getNome());
    }
}