package com.raf.sport_user_service.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                //
                .apis(RequestHandlerSelectors.basePackage("com.raf.sport_user_service.controller"))
                .build()
                .apiInfo(metaData())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    // 1. Definišemo gde se šalje token (u Headeru pod ključem Authorization)
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    // 2. Definišemo koje rute zahtevaju token (u ovom slučaju sve rute)
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    // 3. Povezujemo referencu "JWT" sa opsegom - ovde je globalno
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Sport User Service API",
                "API swagger definition for sport-user-service",
                "1.0.0",
                "Terms of service",
                new Contact("", "", ""),
                "",
                "",
                Collections.emptyList()
        );
    }
}
