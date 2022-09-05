package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
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
public class WebtoonStatusCount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long webtoonId;

    private int joinCount;

    private int leaveCount;

    @Builder
    public WebtoonStatusCount(Long webtoonId) {
        this.webtoonId = webtoonId;
        this.joinCount = 0;
        this.leaveCount = 0;
    }

    public int count() {
        return joinCount - leaveCount;
    }

    public void updateCount(WebtoonStatusType status) {
        switch (status) {
            case JOIN -> joinCount += 1;
            case LEAVE -> leaveCount += 1;
            case JOINED -> joinCount -= 1;
            case LEAVED -> leaveCount -= 1;
        }
    }
}
