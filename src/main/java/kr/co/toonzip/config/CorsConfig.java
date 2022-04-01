package kr.co.toonzip.config;

import kr.co.toonzip.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    private static final String ADAPTING_URL = "/**";

    @NotNull
    private final CorsProperties corsProperties;

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedHeader(corsProperties.getAllowedHeaders());
        config.addAllowedMethod(corsProperties.getAllowedMethods());
        config.addAllowedOrigin(corsProperties.getAllowedOrigins());
        config.setMaxAge(corsProperties.getMaxAge());
        source.registerCorsConfiguration(ADAPTING_URL, config);
        return new CorsFilter(source);
    }
}