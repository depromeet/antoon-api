package kr.co.antoon.cruiser.domain;

import kr.co.antoon.config.properties.SlackCruiserProperties;
import kr.co.antoon.cruiser.dto.slack.SlackCruiserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class SlackCruiser implements CruiserClient {
    private final SlackCruiserProperties slackCruiserProperties;
    private final WebClient.Builder webclient;

    @Override
    public void send(String content) {
        var url = slackCruiserProperties.getWebhookUri();
        var message = new SlackCruiserRequest(content);

        var response = webclient.build()
                .post()
                .uri(url)
                .body(Mono.just(message), SlackCruiserRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(5000))
                .block();

        log.info("[SLACK CRUSIER Content] {} | {}", response, content);
    }
}
