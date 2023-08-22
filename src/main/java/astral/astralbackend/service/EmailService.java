package astral.astralbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

    @Async
    public void enviarEmailParaProdutor(String subject, String email, String content) throws MessagingException{
        MimeMessage mail = mailSender.createMimeMessage();

        MimeMessageHelper message = new MimeMessageHelper(mail);
        message.setSubject(subject);
        message.setText(content, true);
        message.setFrom(supportMail);
        message.setTo(email);

        mailSender.send(mail);
    }

    public String getConteudoEmailProdutor(String nome, Long idFeira){
        String html = """
                <h2><span style="color:#27ae60"><strong>Ol&aacute; %s,</strong></span></h2>
                                
                <p>Chegou um novo pedido para voc&ecirc;!&nbsp;😆</p>
                                
                <p>Venha conferir: <a href="https://astral-frontend-nu.vercel.app/minhas-vendas/%d">https://astral-frontend-nu.vercel.app/minhas-vendas/%d</a></p>
                """.formatted(nome, idFeira, idFeira);
        return html;
    }

}
