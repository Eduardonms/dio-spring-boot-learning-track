package dio.budgeting.infrastructure.http;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI budgetingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Orçamento")
                        .version("v1")
                        .description("API para cadastro e consulta de transações financeiras, com processamento de comandos de voz por IA."));
    }
}
