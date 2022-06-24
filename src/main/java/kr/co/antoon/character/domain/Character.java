package kr.co.antoon.character.domain;

import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.common.domain.BaseEntity;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private CharacterType type;

    private Long webtoonId;

    private Long coinAmount;

    @Builder
    public Character(String name, String thumbnail, CharacterType type, Long webtoonId) {
        this.name = name;
        this.type = type;
        this.thumbnail = thumbnail;
        this.webtoonId = webtoonId;
        this.coinAmount = 0L;
    }

    public void amountUpdate(Long amount) {
        this.coinAmount += amount;
    }
}
