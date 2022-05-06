package kr.co.antoon.common.domain;

import kr.co.antoon.common.dto.SlackCrusierDto;
import kr.co.antoon.config.properties.SlackCrusierProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackCrusier {
    private final SlackCrusierProperties slackCrusierProperties;

    public void send(String content) {
        String url = slackCrusierProperties.getWebhookUri();
        var message = new SlackCrusierDto(content);

        var response = new RestTemplate()
                .postForObject(
                        url,
                        message,
                        String.class
                );

        log.info("[SLACK CRUSIER RESPONSE] {}", response);
    }
}

