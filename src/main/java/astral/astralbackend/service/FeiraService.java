package astral.astralbackend.service;

import astral.astralbackend.entity.Feira;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.repository.FeiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeiraService {

    @Autowired
    private FeiraRepository repository;


    public Feira  habilitarDesabilitarFeira(Long id) {
        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informado não existe!");
        }
        var feira = repository.getReferenceById(id);
        feira.habilitarDesabilitarFeira();
        return feira;
    }

    public void desabilitarFeirasAbertas() {
        List<Feira> feirasAbertas = repository.findAllByAbertaTrue();
        for (Feira feira : feirasAbertas) {
            feira.habilitarDesabilitarFeira();
            repository.save(feira);
        }
    }

    public List<Feira> listarFeiras() {
        return repository.findAll();
    }

    public Feira buscarFeiraAberta() {
        List<Feira> feiras = repository.findAllByAbertaTrue();
        if (feiras.isEmpty()){
            throw new IdNaoEncontradoException("Não possui nenhuma feira aberta!");
        }
        Feira feira = feiras.get(0);
        return feira;
    }
}

