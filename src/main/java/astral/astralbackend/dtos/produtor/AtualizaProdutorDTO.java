package astral.astralbackend.dtos.produtor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizaProdutorDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        String senha) {



}
