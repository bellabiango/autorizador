package br.com.bellabiango.caju.autorizador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autorização de Transações - Caju")
                        .version("v1.0.0")
                        .description("API para processar transações e gerenciar saldos de contas"));
    }
}
