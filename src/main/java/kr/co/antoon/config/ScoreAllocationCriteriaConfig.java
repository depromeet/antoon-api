package kr.co.antoon.config;

import kr.co.antoon.criteria.ScoreAllocationCriteria;
import kr.co.antoon.criteria.BasicAllocateScore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreAllocationCriteriaConfig {
    @Bean
    public ScoreAllocationCriteria scoreAllocationCriteriaV1() {
        return new BasicAllocateScore();
    }
}