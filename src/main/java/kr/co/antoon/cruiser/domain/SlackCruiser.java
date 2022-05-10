package kr.co.antoon.cruiser.domain;

import kr.co.antoon.common.domain.WebClientConfig;
import kr.co.antoon.config.properties.SlackCruiserProperties;
import kr.co.antoon.cruiser.dto.slack.SlackCruiserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackCruiser implements Cruiser {
    private final SlackCruiserProperties slackCruiserProperties;
    private final WebClientConfig webClientConfig;

    @Override
    public void send(String content) {
        var url = slackCruiserProperties.getWebhookUri();
        var message = new SlackCruiserRequest(content);

        webClientConfig.post(url)
                .body(Mono.just(message), SlackCruiserRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response ->
                        log.info("[SLACK CRUSIER Content] {} | {}", response, content)
                );
    }
}

