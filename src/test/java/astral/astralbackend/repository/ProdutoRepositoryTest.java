package astral.astralbackend.repository;

import astral.astralbackend.dtos.produto.CadastroProdutoDTO;
import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import astral.astralbackend.entity.Produto;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.enums.ECategoria;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProdutoRepositoryTest {


    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutorRepository produtorRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolver Produto quando produtor e o produto estiverem disponíveis e ativos")
    void findAllByAtivoTrueAndDisponivelTrueAndProdutorDisponivelTrue() {
        var produtor = cadastrarProdutor("Juninho Capixaba", "27996136533", "juninho@gmail.com", "12345", true, true);
        var produto = cadastrarProduto("Cenoura", new BigDecimal("12"), 10L, "1kg", ECategoria.LEGUMES, produtor, true, true);

        var produtosTeste = produtoRepository.findAllByAtivoTrueAndDisponivelTrueAndProdutorDisponivelTrue();
        assertThat(produtosTeste).contains(produto);
    }
    private Produto cadastrarProduto(String descricao, BigDecimal preco, Long qtdEstoque, String medida,
                                     ECategoria categoria, Produtor produtor, Boolean disponivel, Boolean ativo) {
        var produto = new Produto(descricao, preco, qtdEstoque, medida, categoria, produtor, disponivel, ativo);
        em.persist(produto);
        return produto;
    }

    private Produtor cadastrarProdutor(String nome, String telefone, String email, String senha, Boolean ativo, Boolean disponivel) {
        var produtor = new Produtor(email, senha, nome, telefone, disponivel, ativo);
        em.persist(produtor);
        return produtor;
    }
}