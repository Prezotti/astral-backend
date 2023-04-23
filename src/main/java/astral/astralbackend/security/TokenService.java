package astral.astralbackend.security;

import astral.astralbackend.entity.Produtor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String gerarToken(Produtor produtor){
        try {
            var algoritimo = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("ASTRAL")
                    .withSubject(produtor.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
           throw new RuntimeException("Erro ao gerar o toke JWT: ", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
