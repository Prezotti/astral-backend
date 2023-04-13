package astral.astralbackend.controller;

import astral.astralbackend.dtos.produtor.CadastroDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.dtos.produtor.ListagemProdutorDTO;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.repository.ProdutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("produtor")
public class ProdutorController {
    @Autowired
    private ProdutorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarProdutor(@RequestBody @Valid CadastroDTO dados, UriComponentsBuilder uriBuilder){
        var produtor = new Produtor(dados);
        repository.save(produtor);

        var uri = uriBuilder.path("/produtor/{id}").buildAndExpand(produtor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoProdutorDTO(produtor));
    }
    @GetMapping
    public ResponseEntity<Page<ListagemProdutorDTO>> listarProdutorAtivoEDisponivel(
            @RequestParam(name = "disponivel", required = false) Boolean disponivel,
            @PageableDefault(size = 10) Pageable page
    ){
        Page<Produtor> produtores;
        if (disponivel != null) {
            produtores = repository.findByAtivoTrueAndDisponivel(page, disponivel);
        } else {
            produtores = repository.findAllByAtivoTrue(page);
        }
        var listagem = produtores.map(ListagemProdutorDTO::new);
        return ResponseEntity.ok(listagem);
    }


}
