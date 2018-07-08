package pt.isel.ps.gis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private static final String AUTH_NAME = "Basic auth";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaInfo())
                .securitySchemes(Collections.singletonList(new BasicAuth(AUTH_NAME)))
                .securityContexts(Collections.singletonList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("pt.isel.ps.gis.controllers"))
                .paths(input -> input != null && !input.contains("/error"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference(AUTH_NAME, new AuthorizationScope[0])))
                .forPaths(input -> input != null && !input.equals("/v1"))
                .build();
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Gestão Inteligente de Stocks API")
                .version("1.0")
                .contact(new Contact(
                        "Projeto",
                        "https://github.com/isel42162/projeto",
                        ""
                )).build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/v1/documentation/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/v1/documentation/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/v1/documentation/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/v1/documentation/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/v1/documentation", "/v1/documentation/swagger-ui.html");
        registry.addRedirectViewController("/v1/documentation/", "/v1/documentation/swagger-ui.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/v1/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
    }
}
