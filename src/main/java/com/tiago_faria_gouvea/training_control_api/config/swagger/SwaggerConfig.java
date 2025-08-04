package com.tiago_faria_gouvea.training_control_api.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("API de Treinamento de Funcionários")
            .version("1.0.0")
            .description(
                "Documentação da API para controle de cursos, turmas e participantes."
            )
            .contact(new Contact()
                .name("Tiago Faria Gouvea")
                .email("tfariagouvea@gmail.com")));
  }
}
