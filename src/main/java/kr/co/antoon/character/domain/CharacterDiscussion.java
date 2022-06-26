package kr.co.antoon.character.domain;

import kr.co.antoon.common.domain.BaseEntity;
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
public class CharacterDiscussion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long characterId;

    private Long userId;

    private Integer likeCount = 0;

    @Builder
    public CharacterDiscussion(String content, Long characterId, Long userId) {
        this.content = content;
        this.characterId = characterId;
        this.userId = userId;
    }

    public void update(String content) {
        this.content = content;
    }

    public void updateLikeCount(Boolean flag) {
        if (flag) {
            this.likeCount += 1;
        } else if (likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}
