package kr.co.antoon.recommendation.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long webtoonId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus status;

    @Builder
    public Recommendation(Long webtoonId, Long userId, RecommendationStatus status) {
        this.webtoonId = webtoonId;
        this.userId = userId;
        this.status = status;
    }

    public void updateRecommendationStatus(RecommendationStatus status) {
        this.status = status;
    }
}
