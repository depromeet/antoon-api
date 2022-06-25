package kr.co.antoon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var corsConfig = new CorsConfiguration();

        corsConfig.addAllowedOriginPattern(CorsConfiguration.ALL);
        corsConfig.addAllowedHeader(CorsConfiguration.ALL);
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);

        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        var corsConfigSource = new UrlBasedCorsConfigurationSource();
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
