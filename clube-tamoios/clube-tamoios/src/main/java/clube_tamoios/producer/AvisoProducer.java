package clube_tamoios.producer;

import clube_tamoios.config.RabbitBeanConfiguration;
import clube_tamoios.dto.DocumentoAtualizadoMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AvisoProducer {
    private final RabbitTemplate rabbitTemplate;

    public AvisoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarDocumentoAtualizado(DocumentoAtualizadoMessage evento) {
        DocumentoAtualizadoMessage paraResponsaveis = new DocumentoAtualizadoMessage(
                evento.idPessoa(),
                evento.nomePessoa(),
                evento.idDocumento(),
                evento.tipoDocumento(),
                evento.nomeArquivo(),
                evento.dataAlteracao(),
                evento.listaDiretoria(),
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
                evento.listaDiretoria(),
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


}
