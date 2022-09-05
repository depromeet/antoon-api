package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO : 해당 도메인에 대한 네이밍 변경이 필요할 것 같아요
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long webtoonId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private WebtoonStatusType status;

    @Builder
    public WebtoonStatus(Long webtoonId, Long userId, WebtoonStatusType status) {
        this.webtoonId = webtoonId;
        this.userId = userId;
        this.status = status;
    }

    public void updateStatus(WebtoonStatusType status) {
        this.status = status;
    }
}
