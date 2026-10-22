package clube_tamoios.producer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.dto.DocumentoAtualizadoMessage;
import dto.AvisoMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AvisoProducer {
    private final RabbitTemplate rabbitTemplate;

    public AvisoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(AvisoMessage aviso) {
        rabbitTemplate.convertAndSend(
                RabbitBeanConfiguration.EXCHANGE_AVISO,
                RabbitBeanConfiguration.ROUTING_KEY_AVISO,
                aviso
        );

        System.out.println("[PRODUCER] Aviso enviado: " + aviso);
    }

    public void enviarDocumentoAtualizado(DocumentoAtualizadoMessage evento) {
        DocumentoAtualizadoMessage paraResponsaveis = new DocumentoAtualizadoMessage(
                evento.idPessoa(),
                evento.nomePessoa(),
                evento.idDocumento(),
                evento.tipoDocumento(),
                evento.nomeArquivo(),
                evento.dataAlteracao(),
                "responsavel",
                String.format("%s: %s do desbravador %s foi atualizado. Revise as informações antes de prosseguir.",
                        evento.tipoDocumento() != null && evento.tipoDocumento().equals("fichaMedica") ? "Ficha médica" : "Documento",
                        evento.tipoDocumento() != null && evento.tipoDocumento().equals("fichaMedica") ? "ficha médica" : "documento",
                        evento.nomePessoa()),
                "responsavel",
                true,
                false
        );

        DocumentoAtualizadoMessage paraDiretoria = new DocumentoAtualizadoMessage(
                evento.idPessoa(),
                evento.nomePessoa(),
                evento.idDocumento(),
                evento.tipoDocumento(),
                evento.nomeArquivo(),
                evento.dataAlteracao(),
                "diretoria",
                String.format("Diretoria: a %s do desbravador %s foi %s no sistema.",
                        evento.tipoDocumento() != null && evento.tipoDocumento().equals("fichaMedica") ? "ficha médica" : "documentação",
                        evento.nomePessoa(),
                        evento.tipoAviso() != null && evento.tipoAviso().equals("substituicao") ? "substituída" : "atualizada"),
                "diretoria",
                false,
                true
        );

        rabbitTemplate.convertAndSend(
                RabbitBeanConfiguration.EXCHANGE_DOCUMENTO_ATUALIZADO,
                RabbitBeanConfiguration.ROUTING_KEY_RESPONSAVEIS,
                paraResponsaveis
        );

        rabbitTemplate.convertAndSend(
                RabbitBeanConfiguration.EXCHANGE_DOCUMENTO_ATUALIZADO,
                RabbitBeanConfiguration.ROUTING_KEY_DIRETORIA,
                paraDiretoria
        );

        System.out.println("[PRODUCER] Documento atualizado publicado para responsáveis e diretoria: " + evento);
    }

    public void enviarVarios(AvisoMessage pedido, int quantidade) {
        for (int i = 1; i <= quantidade; i++) {
            AvisoMessage novoPedido = new AvisoMessage(
                    pedido.id() + i,
                    pedido.cliente(),
                    pedido.produto(),
                    pedido.quantidade()
            );

            rabbitTemplate.convertAndSend(
                    RabbitBeanConfiguration.EXCHANGE_AVISO,
                    RabbitBeanConfiguration.ROUTING_KEY_AVISO,
                    novoPedido
            );

            System.out.println("[PRODUCER] Mensagem " + i + " de " + quantidade + " enviada: " + novoPedido);
        }
    }
}
