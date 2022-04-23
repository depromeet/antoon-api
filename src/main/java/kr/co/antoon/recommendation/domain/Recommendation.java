package kr.co.antoon.recommendation.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.Webtoon;
import lombok.AccessLevel;
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

    private int join;   // 탑승해요

    private int leave;  // 잠시 떠나요

    public Recommendation(Long id, Long webtoonId, int join, int leave) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.join = join;
        this.leave = leave;
    }
}
