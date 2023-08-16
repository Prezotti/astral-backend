package astral.astralbackend.service;

import astral.astralbackend.dtos.feira.DetalhamentoFeiraDoProdutorDTO;
import astral.astralbackend.entity.Compra;
import astral.astralbackend.entity.Feira;
import astral.astralbackend.exception.IdNaoEncontradoException;
import astral.astralbackend.repository.FeiraRepository;
import astral.astralbackend.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeiraService {

    @Autowired
    private FeiraRepository feiraRepository;
    @Autowired
    private CompraService compraService;
    @Autowired
    private ProdutorRepository produtorRepository;
    public Feira  habilitarDesabilitarFeira(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informado não existe!");
        }
        var feira = feiraRepository.getReferenceById(id);
        feira.habilitarDesabilitarFeira();
        return feira;
    }

    public void desabilitarFeirasAbertas() {
        List<Feira> feirasAbertas = feiraRepository.findAllByAbertaTrue();
        for (Feira feira : feirasAbertas) {
            feira.habilitarDesabilitarFeira();
            feiraRepository.save(feira);
        }
    }

    public List<Feira> listarFeiras() {
        return feiraRepository.findAll();
    }

    public Feira buscarFeiraAberta() {
        List<Feira> feiras = feiraRepository.findAllByAbertaTrue();
        if (feiras.isEmpty()){
            throw new IdNaoEncontradoException("Não possui nenhuma feira aberta!");
        }
        Feira feira = feiras.get(0);
        return feira;
    }

    public Feira buscarFeiraMaisRecente() {
        Feira feira = feiraRepository.findMaisRecente();
        if (feira == null){
            throw new IdNaoEncontradoException("Não possui nenhuma feira cadastrada!");
        }
        return feira;
    }

    public Feira detalharFeira(Long id) {
        if (!feiraRepository.existsById(id)) {
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        }
        Feira feira = feiraRepository.getReferenceById(id);
        return feira;
    }

    public DetalhamentoFeiraDoProdutorDTO detalharFeiraDoProdutor(Long idProdutor, Long idFeira) {
        if(!feiraRepository.existsById(idFeira)){
            throw new IdNaoEncontradoException("Id da feira informada não existe!");
        } else if (!produtorRepository.existsById(idProdutor)) {
            throw new IdNaoEncontradoException("Id do produtor não existe!");
        }

        BigDecimal valorFeiraDoProdutor = calcularValorFeiraDoProdutor(idProdutor,idFeira);
        Feira feira = feiraRepository.getReferenceById(idFeira);


        return new DetalhamentoFeiraDoProdutorDTO(feira.getId(),feira.getAberta(),feira.getDataAbertura(),valorFeiraDoProdutor);
    }

    public BigDecimal calcularValorFeiraDoProdutor(Long idProdutor, Long idFeira){
        List<Compra> comprasProdutorNaFeira = compraService.listarComprasProdutor(idProdutor, idFeira);
        BigDecimal valorTotalVendas = BigDecimal.ZERO;

        for (Compra compra : comprasProdutorNaFeira) {
            valorTotalVendas = valorTotalVendas.add(compra.getValorTotal());
        }

        return valorTotalVendas;
    }

}

