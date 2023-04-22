package astral.astralbackend.service;

import astral.astralbackend.dtos.produto.AtualizaProdutoDTO;
import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.entity.Produto;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.ProdutoRepository;
import astral.astralbackend.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {

    @Autowired
    ProdutorRepository produtorRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    StorageService storageService;


    public Produto cadastrar(CadastroProdutoDTO dados, MultipartFile file) {

        if (!produtorRepository.existsById(dados.produtorId())) {
            throw new IdNaoEncontradoException("Id do produtor informado não existe!");
        }
        Produtor produtor = produtorRepository.getReferenceById(dados.produtorId());

        if(!ehImagem(file)){
            throw new ValidacaoException("O arquivo deve ser uma imagem JPEG ou PNG");
        }

        String imagem = storageService.uploadImagemProduto(file, produtor);

        Produto produto = new Produto(null, dados.descricao(), dados.preco(), dados.qtdEstoque(), dados.medida(), imagem, dados.categoria(), true, true, produtor);


        produtoRepository.save(produto);

        return produto;

    }

    private boolean ehImagem(MultipartFile file) {
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            return false;
        }
        return true;
    }

    public void deletarProduto(Long id){
        if(!produtoRepository.existsById(id)){
            throw new IdNaoEncontradoException("O id do produto não existe!");
        }
        var produto = produtoRepository.getReferenceById(id);
        storageService.deleteImagemProduto(produto.getImagem());
        produto.excluir();
    }


    public Produto habilitarDesabilitarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id do produto informado não existe!");
        }
        Produto produto = produtoRepository.getReferenceById(id);
        produto.habilitarDesabilitarProduto();

        return produto;
    }

    public Produto atualizarProduto(AtualizaProdutoDTO dados, MultipartFile file) {
        if(!produtoRepository.existsById(dados.id())){
            throw new IdNaoEncontradoException("O id do produto não existe!");
        }

        var produto = produtoRepository.getReferenceById(dados.id());
        String imagem = produto.getImagem();

        if(file != null){
            if(!ehImagem(file)) {
                throw new ValidacaoException("O arquivo deve ser uma imagem JPEG ou PNG");
            }
            storageService.deleteImagemProduto(produto.getImagem());
            imagem = storageService.uploadImagemProduto(file, produto.getProdutor());
        }

        produto.atualizarInformacoes(dados);
        produto.setImagem(imagem);

        return produto;
    }
}
