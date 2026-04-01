package com.andre.projetoacer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("Pet Adoption API")
                .version("v1")
                .description("API de uma rede social voltada para adoção de animais. " +
                        "O sistema permite que dois tipos de usuários — pessoas físicas e instituições — " +
                        "publiquem posts com informações sobre animais disponíveis para adoção, " +
                        "incluindo dados de contato para que a comunicação e o processo de adoção " +
                        "sejam realizados fora da plataforma.")
                .termsOfService("https://www.apache.org/licenses/LICENSE-2.0")
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
