package astral.astralbackend.service;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import astral.astralbackend.entity.Feira;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.repository.FeiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeiraService {
<<<<<<< Updated upstream

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


=======
    @Autowired
    FeiraRepository feiraRepository;

    public Feira habilitarDesabilitarFeira(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id do produto informado não existe!");
        }
        Feira feira = feiraRepository.getReferenceById(id);
        feira.habilitarDesabilitarFeira();

        return feira;
    }

}
>>>>>>> Stashed changes
