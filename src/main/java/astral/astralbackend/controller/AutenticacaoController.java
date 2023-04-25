package astral.astralbackend.controller;

import astral.astralbackend.dtos.usuario.DadosAutenticacao;
import astral.astralbackend.entity.Usuario;
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

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        System.out.println(AuthenticationToken);
        var authentication = manager.authenticate(AuthenticationToken);
        System.out.println(authentication);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        System.out.println(tokenJWT);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
