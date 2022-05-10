package kr.co.antoon.common.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientConfig {
    private final WebClient.Builder builder;

    public WebClient webClient(String url) {
        return builder.baseUrl(url).build();
    }

    public WebClient.RequestBodyUriSpec post(String url) {
        return webClient(url).post();
    }
}