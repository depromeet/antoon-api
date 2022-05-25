package kr.co.antoon.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import kr.co.antoon.oauth.config.AuthUser;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json", "application/xml")
    );

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ResolvedType typeResolver(Class<?> clazz) {
        return new TypeResolver().resolve(clazz);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .useDefaultResponseMessages(false)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver(Pageable.class), typeResolver(SwaggerPageable.class)))
                .ignoredParameterTypes(
                        WebSession.class,
                        ServerHttpRequest.class,
                        ServerHttpResponse.class,
                        ServerWebExchange.class,
                        AuthUser.class
                )
                .apiInfo(new ApiInfo(
                        "ANTOON API",
                        "Management REST API SERVICE",
                        "1.0",
                        "urn:tos",
                        new Contact(
                                "ANTOON API",
                                "https://github.com/depromeet/antoon-api",
                                "wrjssmjdhappy@gmail.com"
                        ),
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        new ArrayList<>()
                ))
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(new ApiKey("JWT", "Authorization", "header")))
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    public String[] whiteListInSwagger() {
        return new String[]{
                "/swagger",
                "/swagger-ui/springfox.css",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/springfox.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-ui.css",
                "/swagger-resources/configuration/ui",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-resources/configuration/security",
                "/swagger-resources",
                "/v2/api-docs",
                "/swagger-ui/index.html",
                "/favicon.ico"
        };
    }

    @Data
    public static class SwaggerPageable {
        private Integer page;
        private Integer size;
    }
}