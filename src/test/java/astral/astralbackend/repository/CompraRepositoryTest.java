package astral.astralbackend.repository;

import astral.astralbackend.dtos.feira.CadastroFeiraDTO;
import astral.astralbackend.entity.*;
import astral.astralbackend.enums.ECategoria;
import astral.astralbackend.enums.EFormaPagamento;
import astral.astralbackend.enums.EOpcaoRecebimento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CompraRepositoryTest {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private FeiraRepository feiraRepository;
    @Autowired
    private TestEntityManager em;
    @Test
    @DisplayName("Devolver null quando a feira não tiver entregas")
    void feiraSemEntregas() {
        var feiraDTO = new CadastroFeiraDTO(BigDecimal.valueOf(10.5));
        var feira = cadastrarFeira(feiraDTO);
        var compra = cadastrarCompra("Joãozinho","27997805450", "Varzea City", "joaozinho@gmail.com", EFormaPagamento.PIX, EOpcaoRecebimento.IFES,
                BigDecimal.valueOf(0), "", feira);

        var compraTeste = compraRepository.findAllEntregasByFeiraId(feira.getId());
        assertThat(compraTeste.isEmpty());
    }


    @Test
    @DisplayName("Devolver compra quando a feira tiver entregas")
    void feiraComEntrega() {
        var feiraDTO = new CadastroFeiraDTO(BigDecimal.valueOf(10.5));
        var feira = cadastrarFeira(feiraDTO);
        var compra = cadastrarCompra("Joãozinho","27997805450", "Varzea City", "joaozinho@gmail.com", EFormaPagamento.PIX, EOpcaoRecebimento.ENTREGA,
                BigDecimal.valueOf(0), "", feira);

        var compraTeste = compraRepository.findAllEntregasByFeiraId(feira.getId());
        assertThat(compraTeste).contains(compra);
    }


    private Compra cadastrarCompra(String cliente, String telefone, String endereco, String emailCliente, EFormaPagamento pagamento, EOpcaoRecebimento opcaoRecebimento, BigDecimal doacao,
                                  String observacao, Feira feira) {
        var compra = new Compra(cliente, telefone, endereco, emailCliente, pagamento, opcaoRecebimento, doacao, observacao, feira);
        em.persist(compra);
        return compra;
    }

    private Feira cadastrarFeira(CadastroFeiraDTO feiraDTO) {
        var feira = new Feira(feiraDTO);
        em.persist(feira);
        return feira;
    }


}