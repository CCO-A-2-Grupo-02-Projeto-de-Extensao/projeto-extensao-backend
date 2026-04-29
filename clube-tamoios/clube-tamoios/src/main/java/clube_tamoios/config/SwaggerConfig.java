package clube_tamoios.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger / OpenAPI.
 *
 * Aqui a gente personaliza o cabeçalho que aparece no topo do Swagger UI:
 * nome do sistema, descrição, versão e contato.
 *
 * Sem essa classe, o Swagger mostraria apenas "Spring API" no título.
 */
@Configuration
public class SwaggerConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI aranduDigitalOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Arandu Digital")
                        .description(
                                "API de gestão do Clube de Desbravadores da Igreja Adventista do Sétimo Dia. " +
                                "Permite o cadastro, autenticação e gerenciamento dos desbravadores e usuários do sistema."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Clube Tamoios")
                                .email("contato@clubetamoios.org")
                        )
                );
    }
}
