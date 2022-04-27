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

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus status;

    @Builder
    public Recommendation(Long webtoonId, Long memberId) {
        this.webtoonId = webtoonId;
        this.memberId = memberId;
    }

    public void changeStatus(RecommendationStatus status) {
        this.status = status;
    }
}
