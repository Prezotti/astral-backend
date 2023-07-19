package astral.astralbackend.controller;

import astral.astralbackend.dtos.feira.CadastroFeiraDTO;
import astral.astralbackend.dtos.feira.DetalhamentoFeiraAbertaDTO;
import astral.astralbackend.dtos.feira.DetalhamentoFeiraDTO;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.repository.FeiraRepository;
import astral.astralbackend.service.FeiraService;
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
@RequestMapping("feira")
public class FeiraController {

    @Autowired
    private FeiraService service;
    @Autowired
    private FeiraRepository repository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity cadastrarFeira(@RequestBody @Valid CadastroFeiraDTO dados, UriComponentsBuilder uriBuilder) {
        service.desabilitarFeirasAbertas();
        var feira = new Feira(dados);
        repository.save(feira);

        var uri = uriBuilder.path("/feira/{id}").buildAndExpand(feira.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoFeiraDTO(feira));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DetalhamentoFeiraDTO>> listarFeiras(@RequestParam(required = false) Boolean aberta) {
        List<Feira> feiras = service.listarFeiras();
        List<DetalhamentoFeiraDTO> listagem = feiras.stream()
                .map(DetalhamentoFeiraDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listagem);
    }

    @GetMapping("/aberta")
    public ResponseEntity<DetalhamentoFeiraAbertaDTO> buscarFeiraAberta(){
        Feira feira = service.buscarFeiraAberta();
        return ResponseEntity.ok(new DetalhamentoFeiraAbertaDTO(feira));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity<DetalhamentoFeiraDTO> habilitarDesabilitarFeira(@PathVariable Long id) {
        Feira feira = service.habilitarDesabilitarFeira(id);
        return ResponseEntity.ok(new DetalhamentoFeiraDTO(feira));
    }

}
