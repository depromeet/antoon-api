package kr.co.antoon.config;

import kr.co.antoon.config.properties.SlackCruiserProperties;
import kr.co.antoon.cruiser.domain.Cruiser;
import kr.co.antoon.cruiser.domain.SlackCruiser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class CruiserConfig {
    private final SlackCruiserProperties slackCruiserProperties;
    private final WebClient.Builder webclient;

    @Bean
    public Cruiser SlackCruiser(){
        return new SlackCruiser(slackCruiserProperties, webclient);
    }
}