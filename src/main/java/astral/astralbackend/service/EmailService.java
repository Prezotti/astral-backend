package astral.astralbackend.service;

import astral.astralbackend.entity.Compra;
import astral.astralbackend.entity.ItemCompra;
import astral.astralbackend.enums.EOpcaoRecebimento;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

    @Async
    public void enviarEmail(String subject, String email, String content) throws MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();

        MimeMessageHelper message = new MimeMessageHelper(mail);
        message.setSubject(subject);
        message.setText(content, true);
        message.setFrom(supportMail);
        message.setTo(email);

        mailSender.send(mail);
    }

    public String getConteudoEmailProdutor(String nome, Long idFeira) {
        String html = """
                <h2><span style="color:#27ae60"><strong>Ol&aacute; %s,</strong></span></h2>
                                
                <p>Chegou um novo pedido para voc&ecirc;!&nbsp;😆</p>
                                
                <p>Venha conferir: <a href="https://astral.ecowebfeira.com/minhas-vendas/%d">https://astral.ecowebfeira.com/minhas-vendas/%d</a></p>
                """.formatted(nome, idFeira, idFeira);
        return html;
    }

    public String getConteudoEmailCliente(Compra compra) {
        Locale ptBrLocale = new Locale("pt", "BR");
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(ptBrLocale);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);

        String html = """
                <h2>🌱<span style="color:#27ae60"><strong>Ol&aacute; %s,</strong></span></h2>
                                
                <p>Seu pedido na feira astral foi realizado com sucesso!&nbsp;</p>
                                
                %s
                                
                <p>Op&ccedil;&atilde;o de recebimento: %s</p>     
                           
                <p>Obrigado por comprar com a gente! Volte sempre!😊</p>
                """.formatted(compra.getCliente(), getTabelaCompra(compra, decimalFormat), compra.getOpcaoRecebimento() + "\n" + compra.getEndereco());
        return html;
    }

    private String getTabelaCompra(Compra compra, DecimalFormat decimalFormat) {
        StringBuilder tableRowsBuilder = new StringBuilder();

        compra.getItens().forEach((ItemCompra item) -> {
            String precoFormatado = decimalFormat.format(item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())));
            String row = String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%d</td>
                        <td>%s</td>
                    </tr>\n
                    """, item.getProduto().getDescricao(), item.getQuantidade(), precoFormatado);
            tableRowsBuilder.append(row);
        });

        if (compra.getDoacao().compareTo(BigDecimal.ZERO) > 0) {
            String doacaoFormatada = decimalFormat.format(compra.getDoacao());
            String row = String.format("""
                    <tr>
                        <td>Doação</td>
                        <td>-</td>
                        <td>%s</td>
                    </tr>\n
                    """, doacaoFormatada);
            tableRowsBuilder.append(row);
        }

        if (compra.getOpcaoRecebimento().equals((EOpcaoRecebimento.ENTREGA))) {
            String taxaEntregaFormatada = decimalFormat.format(compra.getTaxaEntrega());
            String row = String.format("""
                    <tr>
                        <td>Taxa de entrega</td>
                        <td>-</td>
                        <td>%s</td>
                    </tr>\n
                    """, taxaEntregaFormatada);
            tableRowsBuilder.append(row);
        }

        String valorTotalFormatado = decimalFormat.format(compra.getValorTotal());
        String tableRows = tableRowsBuilder.toString();
        return """
                <table border="1" cellpadding="2" cellspacing="1" style="width:700px">
                	<thead>
                		<tr>
                			<th>Produto</th>
                			<th>Qtd</th>
                			<th>Pre&ccedil;o</th>
                		</tr>
                	</thead>
                	<tbody>
                		%s
                		<tr>
                			<td colspan="2" rowspan="1" style="text-align:right">Valor Total do pedido:&nbsp;&nbsp;</td>
                			<td><strong>%s</strong></td>
                		</tr>
                	</tbody>
                </table>
                """.formatted(tableRows, valorTotalFormatado);
    }
}
