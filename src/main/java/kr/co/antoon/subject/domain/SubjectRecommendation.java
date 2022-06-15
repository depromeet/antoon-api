package kr.co.antoon.subject.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubjectRecommendation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long characterId;

    private Long userId;

    public SubjectRecommendation(Long characterId, Long userId) {
        this.characterId = characterId;
        this.userId = userId;
    }
}
