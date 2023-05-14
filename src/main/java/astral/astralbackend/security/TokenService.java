package astral.astralbackend.security;

import astral.astralbackend.entity.Usuario;
import astral.astralbackend.exception.TokenInvalidoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{security.token.secret}")
    private String secret;

    public String getSubject(String token) {
        var algoritimo = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algoritimo)
                    .withIssuer("ASTRAL")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException exception) {
            throw new TokenInvalidoException("Token JWT expirado!");
        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoException("Token JWT inválido!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ASTRAL")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("role", usuario.getClass().getSimpleName())
                    .withClaim("id", usuario.getId())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT: ", exception);
        }
    }
}
