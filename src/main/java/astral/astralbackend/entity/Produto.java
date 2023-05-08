package astral.astralbackend.entity;

import astral.astralbackend.dtos.produto.AtualizaProdutoDTO;
import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.enums.ECategoria;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal preco;

    private Long qtdEstoque;

    private String medida;

    private String imagem;

    @Enumerated(EnumType.STRING)
    private ECategoria categoria;

    private Boolean disponivel;

    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;

    public Produto(CadastroProdutoDTO dados) {

    }

    public void habilitarDesabilitarProduto() {
        this.disponivel = !(this.disponivel);
    }

    public void excluir() {
        this.ativo = false;
    }

    public void atualizarInformacoes(AtualizaProdutoDTO dados) {
        if(dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if(dados.preco() != null){
            this.preco = dados.preco();
        }
        if(dados.qtdEstoque() != null){
            this.qtdEstoque = dados.qtdEstoque();
        }
        if(dados.medida() != null){
            this.medida = dados.medida();
        }
        if(dados.categoria() != null){
            this.categoria = dados.categoria();
        }
    }

    public void removeDoEstoque(Long quantidade) {
        this.qtdEstoque -= quantidade;
    }
}
