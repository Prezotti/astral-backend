package astral.astralbackend.controller;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.repository.FeiraRepository;
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
@RequestMapping("feira")
public class FeiraController {
    @Autowired
    private FeiraRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarFeira(UriComponentsBuilder uriBuilder) {
        var feira = new Feira();
        repository.save(feira);

        var uri = uriBuilder.path("/feira/{id}").buildAndExpand(feira.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoFeiraDTO(feira));
    }

}
