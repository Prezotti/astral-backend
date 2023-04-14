package astral.astralbackend.service;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutorService {
    @Autowired
    private ProdutorRepository produtorRepository;

    public void atualizarProdutor(AtualizaProdutorDTO dados){
        if(!produtorRepository.existsById(dados.id())){
            throw new ValidacaoException("O id do produtor não existe!");
        }

        var produtor = produtorRepository.getReferenceById(dados.id());
        produtor.atualizarInformacoes(dados);
    }
}
