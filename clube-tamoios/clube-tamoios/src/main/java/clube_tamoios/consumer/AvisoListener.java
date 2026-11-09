package clube_tamoios.consumer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.dto.DocumentoAtualizadoMessage;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import clube_tamoios.controller.EmailController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

@Component
public class AvisoListener {

    private final EmailController emailController;

    public AvisoListener(EmailController emailController) {
        this.emailController = emailController;
    }

    @RabbitListener(queues = RabbitBeanConfiguration.QUEUE_RESPONSAVEIS, messageConverter = "jsonMessageConverter")
    public void listenDocumentoResponsaveis(DocumentoAtualizadoMessage evento) {
        System.out.println("[CONSUMER] Aviso para responsáveis");
    }

    @RabbitListener(queues = RabbitBeanConfiguration.QUEUE_DIRETORIA, messageConverter = "jsonMessageConverter")
    public void listenDocumentoDiretoria(DocumentoAtualizadoMessage evento) throws MessagingException {

        // Expressão regular para identificar e-mails
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(evento.listaDiretoria());

        List<String> emails = new ArrayList<>();

        // Encontra todas as ocorrências na String
        while (matcher.find()) {
            emails.add(matcher.group());
        }


        for(String email : emails) {
            emailController.sendEmail(
                    email,
                    "Atualização Documento de " + evento.nomePessoa(),
                    evento.mensagem() +
                            "\n\nNome da Pessoa: " + evento.nomePessoa() +
                            "\n\nNome do Arquivo: " + evento.nomeArquivo() +
                            "\n\nTipo de Arquivo: " + evento.tipoDocumento() +
                            "\n\nData da Alteração: " + evento.dataAlteracao()
            );
        }

    }
}