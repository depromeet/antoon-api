package kr.co.antoon.config.security;

import kr.co.antoon.cache.user.UserRedisCacheService;
import kr.co.antoon.config.web.CorsConfig;
import kr.co.antoon.oauth.application.CustomOAuth2UserService;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.oauth.filter.JwtFilter;
import kr.co.antoon.oauth.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRedisCacheService userRedisCacheService;
    private final CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfig.corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().loginPage("/user/signin")
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(whiteListInSwagger()).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/health", "/api/v1/admin/**").permitAll()
                .antMatchers("/api/v1/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler);
        http.addFilterBefore(new JwtFilter(jwtTokenProvider, userRedisCacheService), UsernamePasswordAuthenticationFilter.class);
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
                "/v3/api-docs",
                "/swagger-ui/index.html",
                "/favicon.ico"
        };
    }

    @Bean
    @ConditionalOnMissingBean(HttpFirewall.class)
    public HttpFirewall configureFirewall() {
        log.info("Registered HttpFirewall");
        
        var strictHttpFirewall = new StrictHttpFirewall();

        strictHttpFirewall.setAllowedHttpMethods(
                Arrays.asList("DELETE", "POST", "GET", "OPTIONS", "PATCH", "PUT")
        );

        return strictHttpFirewall;
    }
}

