package kr.co.antoon.config;

import kr.co.antoon.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var corsConfig = new CorsConfiguration();

        corsConfig.addAllowedOriginPattern(CorsConfiguration.ALL);
        corsConfig.addAllowedHeader(CorsConfiguration.ALL);
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);

        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        var corsConfigSource = new UrlBasedCorsConfigurationSource();
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}