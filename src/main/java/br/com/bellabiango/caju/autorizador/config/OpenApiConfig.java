package br.com.bellabiango.caju.autorizador.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${CODESPACE_NAME:}")
    private String codespaceName;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        String serverUrl;
        String description;

        if (!codespaceName.isEmpty()) {
            // Construir a URL do Codespace
            serverUrl = String.format("https://%s-%s.app.github.dev", codespaceName, serverPort);
            description = "Servidor no Codespace";
        } else {
            // Fallback para localhost
            serverUrl = String.format("http://localhost:%s", serverPort);
            description = "Local";
        }

        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription(description);

        return new OpenAPI().addServersItem(server);
    }
}

