package clube_tamoios.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitBeanConfiguration {

    public static final String QUEUE_AVISO = "queue_aviso";
    public static final String EXCHANGE_AVISO = "exchange_aviso";
    public static final String ROUTING_KEY_AVISO = "routing_key_aviso";

    @Bean
    public Queue pedidosQueue() {
        return new Queue(QUEUE_AVISO, true);
    }

    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(EXCHANGE_AVISO);
    }

    @Bean
    public Binding pedidosBinding(Queue pedidosQueue, DirectExchange pedidosExchange) {
        return BindingBuilder.bind(pedidosQueue)
                .to(pedidosExchange)
                .with(ROUTING_KEY_AVISO);
    }

    // Faz a conversão automática entre objetos Java e JSON nas mensagens.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
