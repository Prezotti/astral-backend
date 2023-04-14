package astral.astralbackend.dtos.produtor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroProdutorDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 5, max = 15)
        String senha
) {
}
