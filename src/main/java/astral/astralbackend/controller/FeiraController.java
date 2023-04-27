package astral.astralbackend.controller;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDTO;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.repository.FeiraRepository;
import astral.astralbackend.service.FeiraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("feira")
public class FeiraController {

    @Autowired
    private FeiraService service;
    @Autowired
    private FeiraRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity cadastrarFeira(UriComponentsBuilder uriBuilder) {
        service.desabilitarFeirasAbertas();
        var feira = new Feira();
        repository.save(feira);

        var uri = uriBuilder.path("/feira/{id}").buildAndExpand(feira.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoFeiraDTO(feira));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity<List<DetalhamentoFeiraDTO>> listarFeiras() {
        List<Feira> feiras = repository.findAll();
        List<DetalhamentoFeiraDTO> listagem = feiras.stream()
                .map(DetalhamentoFeiraDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listagem);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity<DetalhamentoFeiraDTO> habilitarDesabilitarFeira(@PathVariable Long id) {
        Feira feira = service.habilitarDesabilitarFeira(id);
        return ResponseEntity.ok(new DetalhamentoFeiraDTO(feira));
    }

}
