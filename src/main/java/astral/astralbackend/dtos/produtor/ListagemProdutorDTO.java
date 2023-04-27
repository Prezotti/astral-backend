package astral.astralbackend.dtos.produtor;

import astral.astralbackend.entity.Produtor;

public record ListagemProdutorDTO(Long id, String nome, String telefone, String email, boolean disponivel) {

    public  ListagemProdutorDTO(Produtor produtor){
        this(produtor.getId(), produtor.getNome(), produtor.getTelefone(), produtor.getEmail(), produtor.getDisponivel());
    }
}
