package astral.astralbackend.controller;

import astral.astralbackend.dtos.usuario.DadosAutenticacao;
import astral.astralbackend.entity.Administrador;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.security.DadosTokenJWT;
import astral.astralbackend.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private TokenService tokenService;

    @PostMapping("/produtor")
    public ResponseEntity efetuarLoginProdutor(@RequestBody @Valid DadosAutenticacao dados) {
        var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(AuthenticationToken);
        var tokenJWT = tokenService.gerarTokenProdutor((Produtor) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/administrador")
    public ResponseEntity efetuarLoginAdministrador(@RequestBody @Valid DadosAutenticacao dados) {
        var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(AuthenticationToken);
        var tokenJWT = tokenService.gerarTokenAdministrador((Administrador) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
