package astral.astralbackend.controller;

import astral.astralbackend.dtos.compra.ListagemCompraDTO;
import astral.astralbackend.dtos.compra.DetalhamentoCompraDTO;
import astral.astralbackend.dtos.compra.RealizarCompraDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.service.CompraService;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity listarCompras(@PathVariable Long id){
        System.out.println("ID: " + id);
        List<Compra> compras = service.listarCompras(id);
        List<ListagemCompraDTO> listagem = compras.stream()
                .map(ListagemCompraDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listagem);
    }

/*    @GetMapping("/produtor")
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity listarComprasProdutor(@PathVariable Long idProdutor, @PathVariable Long idFeira){

    }*/

}
