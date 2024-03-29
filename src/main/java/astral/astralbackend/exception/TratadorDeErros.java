package astral.astralbackend.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(IdNaoEncontradoException.class)
    public ResponseEntity tratarErroIdNaoEncontrado(IdNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity tratarErroTokenInvalido(TokenInvalidoException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErroMensagemNaoLegivel(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O corpo da requisição não foi fornecido!");
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity tratarErroExcedeuTamanhoDoArquivo(FileSizeLimitExceededException exception){
        return ResponseEntity.status(HttpStatus.REQUEST_ENTITY_TOO_LARGE).body("O Tamanho do arquivo excedeu o limite permitido de " + exception.getPermittedSize()/(1024*1024) + "MB!");
    }

    private record DadosErroValidadacao(String campo, String mensagem) {

        public DadosErroValidadacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
