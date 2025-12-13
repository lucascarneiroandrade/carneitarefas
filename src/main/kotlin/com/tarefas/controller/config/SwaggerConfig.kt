package com.tarefas.controller.config



import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("api-v1")
            .packagesToScan("com.tarefas.controller")
            .pathsToMatch("/**")
            .build()
    }

    @Bean
    fun customOpenAPI(): OpenAPI{
        return OpenAPI()
            .info(
                Info()
                    .title("API de Tarefas")
                    .description("Documentação da API para gerenciamento de tarefas")
                    .version("v1.0")
            )
    }
}