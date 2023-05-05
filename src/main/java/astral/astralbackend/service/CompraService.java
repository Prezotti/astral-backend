package astral.astralbackend.service;

import astral.astralbackend.dtos.compra.CadastroItemCompraDTO;
import astral.astralbackend.dtos.compra.RealizarCompraDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.entity.ItemCompra;
import astral.astralbackend.entity.Produto;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.CompraRepository;
import astral.astralbackend.repository.FeiraRepository;
import astral.astralbackend.repository.ProdutoRepository;
import astral.astralbackend.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private FeiraRepository feiraRepository;
    @Autowired
    private ProdutorRepository produtorRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Compra realizarCompra(RealizarCompraDTO dados) {

        if (!feiraRepository.existsById(dados.feiraId())) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        Feira feiraAtual = feiraRepository.getReferenceById(dados.feiraId());

        Compra compra = new Compra(dados.cliente(), dados.telefone(), dados.endereco(), dados.formaPagamento(), dados.opcaoRecebimento(), dados.doacao(), dados.observacoes(), feiraAtual);

        for (CadastroItemCompraDTO item : dados.itens()) {
            if (!produtoRepository.existsById(item.produtoId())) {
                throw new IdNaoEncontradoException("Id do produto informado não existe:" + item.produtoId() + "!");
            }
            Produto produto = produtoRepository.getReferenceById(item.produtoId());
            if (item.quantidade() > produto.getQtdEstoque()) {
                throw new ValidacaoException(produto.getDescricao() + " excedeu o estoque!");
            }
            ItemCompra itemCompra = new ItemCompra(produto, compra, item.quantidade());
            compra.adicionarItem(itemCompra);
        }
        BigDecimal valorCompra = compra.calculaValorTotal();
        feiraAtual.adicionaValorCompra(valorCompra);

        compraRepository.save(compra);

        return compra;

    }

    public List<Compra> listarCompras(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        List<Compra> compras = compraRepository.findAllByFeiraId(id);
        return compras;
    }

    public List<Compra> listarComprasProdutor(Long idProdutor, Long idFeira) {
        if(!feiraRepository.existsById(idFeira)){
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        } else if(!produtorRepository.existsById(idProdutor)){
            throw new IdNaoEncontradoException("Id do produto informado não existe!");
        }
        List<Compra> compras = compraRepository.findAllByProdutorIdAndFeiraId(idProdutor, idFeira);
        System.out.println(compras);
        return compras;
    }
    
}

