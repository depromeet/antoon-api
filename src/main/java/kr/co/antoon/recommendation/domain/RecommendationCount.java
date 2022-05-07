package kr.co.antoon.recommendation.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long webtoonId;

    private Long userId;

    private int joinUserCount = 0;

    private int leaveUserCount = 0;

    @Builder
    public RecommendationCount(Long userId, Long webtoonId, int joinUserCount) {
        this.userId = userId;
        this.webtoonId = webtoonId;
        this.joinUserCount = joinUserCount;
    }

    public void plusJoinCount(int joinCount) {
        this.joinUserCount = joinCount;
    }

    public void plusLeaveCount(int leaveCount) {
        this.leaveUserCount = leaveCount;
    }

    public void minusJoinCount(int joinCount) {
        this.joinUserCount = joinCount;
    }

    public void minusLeaveCount(int leaveCount) {
        this.leaveUserCount = leaveCount;
    }
}
