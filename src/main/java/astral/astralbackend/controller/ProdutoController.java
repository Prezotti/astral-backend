package astral.astralbackend.controller;

import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.dtos.produto.DetalhamentoProdutoDTO;
import astral.astralbackend.dtos.produto.ListagemProdutoDTO;
import astral.astralbackend.entity.Produto;
import astral.astralbackend.repository.ProdutoRepository;
import astral.astralbackend.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarProduto(@RequestPart("dados") @Valid CadastroProdutoDTO produtoDTO,
                                           @RequestPart("file") @NotNull MultipartFile file,
                                           UriComponentsBuilder uriBuilder) {
        var produto = service.cadastrar(produtoDTO, file);
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoProdutoDTO(produto));
    }

    @GetMapping
    public ResponseEntity<List<ListagemProdutoDTO>> listarProdutosAtivos() {
        List<Produto> produtos = repository.findAllByAtivoTrueAndDisponivelTrue();
        List<ListagemProdutoDTO> listagem = produtos.stream()
                .map(ListagemProdutoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listagem);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalhamentoProdutoDTO> habilitarDesabilitarProduto(@PathVariable Long id) {
        Produto produto = service.habilitarDesabilitarProduto(id);
        return ResponseEntity.ok(new DetalhamentoProdutoDTO(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarProdutor(@PathVariable Long id) {
        service.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }


}
