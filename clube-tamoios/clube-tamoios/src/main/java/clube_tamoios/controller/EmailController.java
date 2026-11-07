package clube_tamoios.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import clube_tamoios.service.EmailService;

@RestController
@RequestMapping("/email")
@Tag(name = "Email", description = "Envio de emails do clube")
@SecurityRequirement(name = "bearerAuth")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-email")
    @Operation(summary = "Enviar email simples")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) throws MessagingException {
        emailService.sendSimpleEmail(to, subject, text);
        return "Email enviado com sucesso";
    }
}
