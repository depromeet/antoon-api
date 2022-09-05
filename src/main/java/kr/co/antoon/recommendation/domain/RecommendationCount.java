package kr.co.antoon.recommendation.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "i_webtoon_id", columnList = "webtoonId", unique = true))
public class RecommendationCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long webtoonId;

    private int joinCount;

    private int leaveCount;

    @Builder
    public RecommendationCount(Long webtoonId) {
        this.webtoonId = webtoonId;
        this.joinCount = 0;
        this.leaveCount = 0;
    }

    public int count() {
        return joinCount - leaveCount;
    }

    public void updateCount(RecommendationStatus status) {
        switch (status) {
            case JOIN -> joinCount += 1;
            case LEAVE -> leaveCount += 1;
            case JOINED -> joinCount -= 1;
            case LEAVED -> leaveCount -= 1;
        }
    }
}
