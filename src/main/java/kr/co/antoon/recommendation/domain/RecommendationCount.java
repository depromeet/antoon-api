package kr.co.antoon.recommendation.domain;

import kr.co.antoon.common.domain.BaseEntity;
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

    private int joinCount = 0;

    private int leaveCount = 0;

    @Builder
    public RecommendationCount(Long webtoonId, int joinUserCount) {
        this.webtoonId = webtoonId;
        this.joinCount = joinUserCount;
    }

    public void plusJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public void plusLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public void minusJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public void minusLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public int count() {
        return joinCount - leaveCount;
    }
}