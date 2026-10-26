package clube_tamoios.producer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.dto.DocumentoAtualizadoMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AvisoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AvisoProducer avisoProducer;

    @Test
    void enviarDocumentoAtualizado_devePublicarMensagemPersonalizadaParaCadaFila() {
        DocumentoAtualizadoMessage evento = new DocumentoAtualizadoMessage(
                55,
                "André Luiz Cunha",
                3,
                "fichaMedica",
                "PDF_TESTE.pdf",
                LocalDateTime.of(2026, 10, 20, 19, 46, 6),
                "Roberto Costa - roberto.costa@clube.com, Fernanda Alves - fernanda.alves@clube.com",
                "upload",
                "Documento atualizado",
                "responsavel",
                true,
                false
        );

        avisoProducer.enviarDocumentoAtualizado(evento);

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);

        verify(rabbitTemplate, times(2)).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                messageCaptor.capture()
        );

        List<String> routingKeys = routingKeyCaptor.getAllValues();
        List<Object> messages = messageCaptor.getAllValues();

        assertEquals(RabbitBeanConfiguration.EXCHANGE_DOCUMENTO_ATUALIZADO, exchangeCaptor.getAllValues().get(0));
        assertTrue(routingKeys.contains(RabbitBeanConfiguration.ROUTING_KEY_RESPONSAVEIS));
        assertTrue(routingKeys.contains(RabbitBeanConfiguration.ROUTING_KEY_DIRETORIA));

        DocumentoAtualizadoMessage responsavel = (DocumentoAtualizadoMessage) messages.get(0);
        DocumentoAtualizadoMessage diretoria = (DocumentoAtualizadoMessage) messages.get(1);

        assertEquals("responsavel", responsavel.tipoAviso());
        assertTrue(responsavel.mensagem().contains("ficha médica"));
        assertTrue(responsavel.mensagem().contains("André Luiz Cunha"));


        assertEquals("diretoria", diretoria.tipoAviso());
        assertTrue(diretoria.mensagem().contains("Diretoria"));
        assertTrue(diretoria.mensagem().contains("ficha médica"));
        assertTrue(diretoria.mensagem().contains("atualizada"));
    }
}
