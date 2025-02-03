package io.dev.env.receipt_processor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Receipt Processor API")
                        .version("1.0")
                        .description("API for processing receipts and calculating points")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
