package astral.astralbackend.controller;

import astral.astralbackend.dtos.compra.DetalhamentoCompraDTO;
import astral.astralbackend.dtos.compra.RealizarCompraDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.service.CompraService;
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
@RequestMapping("compra")
public class CompraController {

    @Autowired
    CompraService service;

    @PostMapping
    @Transactional
    public ResponseEntity realizarCompra(@RequestBody @Valid RealizarCompraDTO dados, UriComponentsBuilder uriBuilder) {
        Compra compra = service.realizarCompra(dados);

        var uri = uriBuilder.path("/feira/{id}").buildAndExpand(compra.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoCompraDTO(compra));
    }

}
