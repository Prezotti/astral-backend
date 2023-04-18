package astral.astralbackend.exception;

import org.webjars.NotFoundException;

public class IdNaoEncontradoException extends NotFoundException {
    public IdNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
