package clube_tamoios.consumer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.entity.Aviso;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AvisoListener {

        @RabbitListener(queues = RabbitBeanConfiguration.QUEUE_AVISO, messageConverter = "jsonMessageConverter")
    public void listenAviso(
            Aviso aviso
    ) {

        System.out.println(
                """
                        Aviso Recebido!
                            ID: %d
                            nome: %s
                            descricao: %s
                            data: %s
                        """.formatted(aviso.id(), aviso.nome(), aviso.descricao(), aviso.dataCriacao()
                )
        );
    }
}