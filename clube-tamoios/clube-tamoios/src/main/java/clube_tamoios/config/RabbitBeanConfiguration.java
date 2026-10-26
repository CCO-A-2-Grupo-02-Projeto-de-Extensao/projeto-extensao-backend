package clube_tamoios.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitBeanConfiguration {

    public static final String QUEUE_RESPONSAVEIS = "queue_responsaveis_documentos";
    public static final String QUEUE_DIRETORIA = "queue_diretoria_documentos";
    public static final String EXCHANGE_DOCUMENTO_ATUALIZADO = "exchange_documento_atualizado";
    public static final String ROUTING_KEY_RESPONSAVEIS = "routing_key_responsaveis_documentos";
    public static final String ROUTING_KEY_DIRETORIA = "routing_key_diretoria_documentos";

    @Bean
    public Queue filaResponsaveis() {
        return new Queue(QUEUE_RESPONSAVEIS, true);
    }

    @Bean
    public Queue filaDiretoria() {
        return new Queue(QUEUE_DIRETORIA, true);
    }

    @Bean
    public DirectExchange exchangeDocumentoAtualizado() {
        return new DirectExchange(EXCHANGE_DOCUMENTO_ATUALIZADO);
    }


    @Bean
    public Binding responsaveisBinding(Queue filaResponsaveis, DirectExchange exchangeDocumentoAtualizado) {
        return BindingBuilder.bind(filaResponsaveis)
                .to(exchangeDocumentoAtualizado)
                .with(ROUTING_KEY_RESPONSAVEIS);
    }

    @Bean
    public Binding diretoriaBinding(Queue filaDiretoria, DirectExchange exchangeDocumentoAtualizado) {
        return BindingBuilder.bind(filaDiretoria)
                .to(exchangeDocumentoAtualizado)
                .with(ROUTING_KEY_DIRETORIA);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
