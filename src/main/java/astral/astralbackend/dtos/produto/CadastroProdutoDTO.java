package astral.astralbackend.dtos.produto;

import astral.astralbackend.enums.ECategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record CadastroProdutoDTO(
    @NotBlank
    String descricao,
    @NotNull
    BigDecimal preco,
    @NotNull
    Long qtdEstoque,
    @NotBlank
    String medida,
    ECategoria categoria,
    @NotNull
    Long produtorId
){

}
