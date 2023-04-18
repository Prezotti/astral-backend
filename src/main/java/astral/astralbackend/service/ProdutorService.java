package astral.astralbackend.service;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.dtos.produtor.ListagemProdutorDTO;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.exception.ValidacaoException;
import astral.astralbackend.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutorService {
    @Autowired
    private ProdutorRepository produtorRepository;

    public Produtor atualizarProdutor(AtualizaProdutorDTO dados){
        if(!produtorRepository.existsById(dados.id())){
            throw new ValidacaoException("O id do produtor não existe!");
        }

        var produtor = produtorRepository.getReferenceById(dados.id());
        produtor.atualizarInformacoes(dados);

        return produtor;
    }

    public void deletarProdutor(Long id) {
        if(!produtorRepository.existsById(id)){
            throw new ValidacaoException("O id do produtor não existe!");
        }
        var produtor = produtorRepository.getReferenceById(id);
        produtor.excluir();
    }

    public List<ListagemProdutorDTO> listarProdutor(Boolean disponivel) {
        List<Produtor> produtores;
        if (disponivel != null) {
            produtores = (List<Produtor>) produtorRepository.findByAtivoTrueAndDisponivel(disponivel);
        } else {
            produtores = (List<Produtor>) produtorRepository.findAllByAtivoTrue();
        }
        return produtores.stream()
                .map(ListagemProdutorDTO::new)
                .collect(Collectors.toList());
    }
}
