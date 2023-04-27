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
            """)
    List<Produto> findAllByAtivoTrueAndDisponivelTrueAndProdutorDisponivelTrue();
}
