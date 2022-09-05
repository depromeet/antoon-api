package kr.co.antoon.config;

import kr.co.antoon.criteria.BasicAllocateScore;
import kr.co.antoon.criteria.ScoreAllocationCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ScoreAllocationCriteriaConfig {
    @Bean
    @ConditionalOnMissingBean(ScoreAllocationCriteria.class)
    public ScoreAllocationCriteria scoreAllocationCriteria() {
        log.info("Registered ScoreAllocationCriteria");
        return new BasicAllocateScore();
    }
}
