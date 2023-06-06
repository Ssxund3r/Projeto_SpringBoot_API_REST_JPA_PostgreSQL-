package curso.api.rest.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Cadastro de Usuário")
                        .description("Projeto em Spring Restful jpa")
                        .contact(new Contact()
                                .name("Gabriel Filipe da costa")
                                .email("gabrielfillip@gmail.com"))
                        .version("1.0.0"));

    }				
}
