package kr.co.antoon.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties(prefix = "cruiser")
public class SlackCruiserProperties {
    @NotBlank
    private String webhookUri;
}