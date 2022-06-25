package kr.co.antoon.webtoon.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonSnapshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    private Long webtoonId;

    private LocalDate snapshotTime;

    public WebtoonSnapshot(Double score, Long webtoonId) {
        this.score = score;
        this.webtoonId = webtoonId;
        this.snapshotTime = LocalDate.now();
    }
}
