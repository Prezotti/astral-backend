package astral.astralbackend.controller;

import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.dtos.produto.DetalhamentoProdutoDTO;
import astral.astralbackend.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarProduto(@RequestPart("dados") @Valid CadastroProdutoDTO produtoDTO,
                                           @RequestPart("file") @NotNull MultipartFile file,
                                           UriComponentsBuilder uriBuilder) {
        var produto = service.cadastrar(produtoDTO, file);
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoProdutoDTO(produto));
    }
}
