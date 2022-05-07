package kr.co.antoon.cruiser.domain;

import kr.co.antoon.cruiser.dto.slack.SlackCruiserRequest;
import kr.co.antoon.config.properties.SlackCruiserProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackCruiser implements Cruiser {
    private final SlackCruiserProperties slackCruiserProperties;

    @Override
    public void send(String content) {
        String url = slackCruiserProperties.getWebhookUri();
        var message = new SlackCruiserRequest(content);

        var response = new RestTemplate()
                .postForObject(
                        url,
                        message,
                        String.class
                );

        log.info("[SLACK CRUSIER Content] {} | {}", response, content);
    }
}

