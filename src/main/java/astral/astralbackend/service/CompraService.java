package astral.astralbackend.service;

import astral.astralbackend.dtos.compra.CadastroItemCompraDTO;
import astral.astralbackend.dtos.compra.RealizarCompraDTO;
import astral.astralbackend.entity.*;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.CompraRepository;
import astral.astralbackend.repository.FeiraRepository;
import astral.astralbackend.repository.ProdutoRepository;
import astral.astralbackend.repository.ProdutorRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private EmailService emailService;

    public Compra realizarCompra(RealizarCompraDTO dados) {

        if (!feiraRepository.existsById(dados.feiraId())) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        Feira feiraAtual = feiraRepository.getReferenceById(dados.feiraId());

        String endereco = "";
        if (dados.endereco() != null) {
            endereco = dados.endereco();
        }

        BigDecimal doacao = BigDecimal.ZERO;
        if (dados.doacao() != null) {
            doacao = dados.doacao();
        }

        Compra compra = new Compra(dados.cliente(), dados.telefone(), endereco, dados.emailCliente(), dados.formaPagamento(), dados.opcaoRecebimento(), doacao, dados.observacoes(), feiraAtual);

        for (CadastroItemCompraDTO item : dados.itens()) {
            if (!produtoRepository.existsById(item.produtoId())) {
                throw new IdNaoEncontradoException("Id do produto informado não existe:" + item.produtoId() + "!");
            }
            Produto produto = produtoRepository.getReferenceById(item.produtoId());
            if (item.quantidade() > produto.getQtdEstoque()) {
                throw new ValidacaoException(produto.getDescricao() + " excedeu o estoque!");
            }
            produto.removeDoEstoque(item.quantidade());
            produtoRepository.save(produto);

            ItemCompra itemCompra = new ItemCompra(produto, compra, item.quantidade());
            compra.adicionarItem(itemCompra);
        }

        compra.calculaValorTotal();
        feiraAtual.atualizaTotais(compra);

        compraRepository.save(compra);
        feiraRepository.save(feiraAtual);

        notificarProdutores(feiraAtual.getId(), compra.getId());
        notificarCliente(compra);

        return compra;

    }

    private void notificarProdutores(Long idFeira, Long idCompra) {
        List<Produtor> produtoresParticipantes = compraRepository.findAllProdutoresbyFeiraIdAndCompraId(idFeira, idCompra);
        if (!produtoresParticipantes.isEmpty()) {
            produtoresParticipantes.forEach(produtor -> {
                String content = emailService.getConteudoEmailProdutor(produtor.getNome(), idFeira);
                try {
                    emailService.enviarEmail("Notificação de venda", produtor.getEmail(), content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void notificarCliente(Compra compra) {
        String content = emailService.getConteudoEmailCliente(compra);
        try {
            emailService.enviarEmail("Notificação de compra - ASTRAL", compra.getEmailCliente(), content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> listarCompras(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        List<Compra> compras = compraRepository.findAllByFeiraId(id);
        return compras;
    }

    public List<Compra> listarComprasProdutor(Long idProdutor, Long idFeira) {
        if (!feiraRepository.existsById(idFeira)) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        } else if (!produtorRepository.existsById(idProdutor)) {
            throw new IdNaoEncontradoException("Id do produtor informado não existe!");
        }
        List<Compra> compras = compraRepository.findAllByProdutorIdAndFeiraId(idProdutor, idFeira);
        List<Compra> comprasProdutor = filtrarComprasPorProdutor(compras, idProdutor);
        return comprasProdutor;
    }

    public List<Compra> filtrarComprasPorProdutor(List<Compra> compras, Long idProdutor) {
        return compras.stream()
                .filter(compra -> compra.getItens().stream()
                        .anyMatch(item -> item.getProduto().getProdutor().getId().equals(idProdutor)))
                .map(compra -> {
                    List<ItemCompra> itensFiltrados = compra.getItens().stream()
                            .filter(item -> item.getProduto().getProdutor().getId().equals(idProdutor))
                            .collect(Collectors.toList());
                    compra.setItens(itensFiltrados);

                    BigDecimal valorTotal = BigDecimal.ZERO; // Inicializa o valor total

                    for (ItemCompra item : itensFiltrados) {
                        BigDecimal valorItem = item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                        valorTotal = valorTotal.add(valorItem);
                    }

                    compra.setValorTotal(valorTotal); // Define o valor total da compra

                    return compra;
                })
                .filter(compra -> !compra.getItens().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Compra> listarEntregas(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        List<Compra> compras = compraRepository.findAllEntregasByFeiraId(id);
        return compras;
    }
}

