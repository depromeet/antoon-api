package kr.co.antoon.recommendation.domain;

import kr.co.antoon.common.domain.BaseEntity;
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

//    private int joinMemberCount;   // 탑승한 인원 수

//    private int leaveMemberCount;  // 하차한 인원 수

    @Builder
    public Recommendation(Long webtoonId, Long memberId) {
        this.webtoonId = webtoonId;
        this.memberId = memberId;
//        this.joinMemberCount = joinMemberCount;
//        this.leaveMemberCount = leaveCount;
    }
}
