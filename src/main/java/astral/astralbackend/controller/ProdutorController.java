package astral.astralbackend.controller;

import astral.astralbackend.dtos.produtor.CadastroDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.repository.ProdutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
