package kr.co.antoon.character.domain;

import kr.co.antoon.character.domain.vo.CharacterImageType;
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
public class CharacterImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CharacterImageType type;

    private Long characterId;

    @Builder
    public CharacterImage(String imageUrl, CharacterImageType type, Long characterId) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.characterId = characterId;
    }
}
