package astral.astralbackend.dtos.produtor;

import astral.astralbackend.entity.Produtor;

public record DetalhamentoProdutorDTO(Long id, String nome, String telefone, String email) {
    public DetalhamentoProdutorDTO(Produtor produtor) {
        this(produtor.getId(), produtor.getNome(), produtor.getTelefone(), produtor.getEmail());
    }
}
