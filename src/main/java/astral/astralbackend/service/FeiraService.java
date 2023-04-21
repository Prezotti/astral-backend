package astral.astralbackend.service;


import astral.astralbackend.entity.Feira;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.repository.FeiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeiraService {

    @Autowired
    private FeiraRepository repository;


    public void  habilitarDesabilitarFeira(Long id) {
        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informado não existe!");
        }
        var feira = repository.getReferenceById(id);
        feira.habilitarDesabilitarFeira();
    }
}


