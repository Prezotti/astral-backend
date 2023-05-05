package astral.astralbackend.repository;

import astral.astralbackend.entity.Compra;
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


}
