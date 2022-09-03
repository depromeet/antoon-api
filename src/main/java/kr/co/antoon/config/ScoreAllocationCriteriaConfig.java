package kr.co.antoon.config;

import kr.co.antoon.criteria.BasicAllocateScore;
import kr.co.antoon.criteria.ScoreAllocationCriteria;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreAllocationCriteriaConfig {
    @Bean
    @ConditionalOnMissingBean(ScoreAllocationCriteria.class)
    public ScoreAllocationCriteria scoreAllocationCriteria() {
        return new BasicAllocateScore();
    }
}
