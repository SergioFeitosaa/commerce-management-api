package br.com.sergio.customer_management_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi () {
        return new OpenAPI().
                info(new Info().
                        title("Commerce Management API")
                        .description("Manage customers, products, orders and payments in a commercial system.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sérgio Feitosa")
                                .email("sergio.feitosa2@gmail.com")
                                .url("https://github.com/SergioFeitosaa")));
    }

}
