package astral.astralbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(LoginInvalidoException.class)
    public ResponseEntity tratarLoginInvalido(LoginInvalidoException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(IdNaoEncontradoException.class)
    public ResponseEntity tratarErroIdNaoEncontrado(IdNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidadacao::new).toList());
    }

    private record DadosErroValidadacao(String campo, String mensagem) {

        public DadosErroValidadacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
