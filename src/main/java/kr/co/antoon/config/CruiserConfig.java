package kr.co.antoon.config;

import kr.co.antoon.config.properties.SlackCruiserProperties;
import kr.co.antoon.cruiser.domain.CruiserClient;
import kr.co.antoon.cruiser.domain.SlackCruiser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CruiserConfig {
    private final SlackCruiserProperties slackCruiserProperties;
    private final WebClient.Builder webclient;

    @Bean
    @ConditionalOnMissingBean(CruiserClient.class)
    public CruiserClient slackCruiser() {
        log.info("Registered slackCruiser");
        return new SlackCruiser(slackCruiserProperties, webclient);
    }
}
