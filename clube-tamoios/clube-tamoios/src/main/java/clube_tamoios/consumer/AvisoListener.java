package clube_tamoios.consumer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.dto.DocumentoAtualizadoMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AvisoListener {

    @RabbitListener(queues = RabbitBeanConfiguration.QUEUE_RESPONSAVEIS, messageConverter = "jsonMessageConverter")
    public void listenDocumentoResponsaveis(DocumentoAtualizadoMessage evento) {
        System.out.println("[CONSUMER] Aviso para responsáveis: " + evento);
    }

    @RabbitListener(queues = RabbitBeanConfiguration.QUEUE_DIRETORIA, messageConverter = "jsonMessageConverter")
    public void listenDocumentoDiretoria(DocumentoAtualizadoMessage evento) {
        System.out.println("[CONSUMER] Aviso para diretoria: " + evento);
    }
}