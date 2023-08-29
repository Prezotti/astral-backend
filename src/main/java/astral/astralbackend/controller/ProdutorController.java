package astral.astralbackend.controller;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.dtos.produtor.ListagemProdutorDTO;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.ProdutorRepository;
import astral.astralbackend.service.ProdutorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("produtor")
public class  ProdutorController {

    @Autowired
    private ProdutorService service;

    @Autowired
    private ProdutorRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity cadastrarProdutor(@RequestBody @Valid CadastroProdutorDTO dados, UriComponentsBuilder uriBuilder) {
        if(repository.existsByEmailAndAtivoTrue(dados.email())){
            throw new ValidacaoException("Este email já está cadastrado para outro produtor ativo!");
        }
        var produtor = new Produtor(dados);
        repository.save(produtor);

        var uri = uriBuilder.path("/produtor/{id}").buildAndExpand(produtor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoProdutorDTO(produtor));
    }

    @GetMapping
    public ResponseEntity<List<ListagemProdutorDTO>> listarProdutorAtivoEDisponivel(
            @RequestParam(name = "disponivel", required = false) Boolean disponivel
    ) {
        var produtor = service.listarProdutor(disponivel);
        var listagem = produtor.stream().map(ListagemProdutorDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listagem);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity<DetalhamentoProdutorDTO> detalharProdutor(
            @PathVariable Long id) {
        var produtor = service.detalharProdutor(id);
        return ResponseEntity.ok(new DetalhamentoProdutorDTO(produtor));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity atualizarProdutor(@RequestBody @Valid AtualizaProdutorDTO dados) {
        var produtor = service.atualizarProdutor(dados);


        return ResponseEntity.ok(new DetalhamentoProdutorDTO(produtor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity deletarProdutor(@PathVariable Long id) {
        service.deletarProdutor(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    @Transactional
    public ResponseEntity<DetalhamentoProdutorDTO> disponivelNaoDisponivel(@PathVariable Long id) {
        Produtor produtor = service.disponivelNaoDisponivel(id);
        return ResponseEntity.ok(new DetalhamentoProdutorDTO(produtor));
    }


}
