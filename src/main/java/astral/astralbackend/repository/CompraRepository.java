package astral.astralbackend.repository;

import astral.astralbackend.entity.Compra;
import astral.astralbackend.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findAllByFeiraId(Long id);

    @Query("""
            SELECT c FROM Compra c
            JOIN c.feira f
            JOIN c.itens i
            JOIN i.produto produto
            WHERE f.id = :idFeira
            AND produto.produtor.id = :idProdutor
         """)
    List<Compra> findAllByProdutorIdAndFeiraId(Long idProdutor, Long idFeira);

    @Query("""
            SELECT c FROM Compra c
            WHERE  c.opcaoRecebimento = 'ENTREGA'
            AND c.feira.id = :id
            """)
    List<Compra> findAllEntregasByFeiraId(Long id);

    @Query("""
            SELECT DISTINCT produto.produtor FROM Compra c
            JOIN c.feira f
            JOIN c.itens i
            JOIN i.produto produto
            WHERE c.feira.id = :id
            """)
    List<Produtor> findAllProdutoresbyFeiraId(Long id);
}
