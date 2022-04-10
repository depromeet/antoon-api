package kr.co.antoon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(
                        WebSession.class,
                        ServerHttpRequest.class,
                        ServerHttpResponse.class,
                        ServerWebExchange.class
                )
                .apiInfo(new ApiInfo(
                                "ANTOON TITLE",
                                "ANTOON CORE API",
                                "v.1.0",
                                "urn:tos",
                                new Contact(
                                        "ANTOON",
                                        "https://github.com/depromeet/antoon-api",
                                        "wrjssmjdhappy@gmail.com"
                                ),
                                "Apache2.0",
                                "http://www.apache.org/licenses/LICENSE-2.0",
                                new ArrayList<>()
                        )
                )
                .produces(new HashSet<>(Arrays.asList("application/json", "application/xml")))
                .consumes(new HashSet<>(Arrays.asList("application/json", "application/xml")))
                .securitySchemes(List.of(new ApiKey(
                        "ANTOON-API",
                        "Authorization",
                        "header"
                )))
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }
}