package astral.astralbackend.repository;

import astral.astralbackend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
            SELECT p
            FROM Produto p
            WHERE p.ativo = true
            AND p.disponivel = true
            AND p.produtor.disponivel = true
            AND p.qtdEstoque > 0
            """)
    List<Produto> findAllByAtivoTrueAndDisponivelTrueAndProdutorDisponivelTrue();

    List<Produto> findAllByAtivoTrueAndDisponivelTrueAndProdutorId(Long id);

    List<Produto> findAllByAtivoTrueAndDisponivelFalseAndProdutorId(Long id);

    List<Produto> findAllByAtivoTrueAndProdutorId(Long id);
}
