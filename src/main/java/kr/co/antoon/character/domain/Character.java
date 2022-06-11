package kr.co.antoon.character.domain;

import kr.co.antoon.character.domain.vo.VoteType;
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
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "character_couple")
public class Character extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    private String title;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private VoteType type;

    private Long webtoonId;

    private Long amount;

    // TODO: builder가 필요한가? 운영에서 데이터를 넣어주는데?
    @Builder
    public Character(String name, String title, String imageUrl, VoteType type, Long webtoonId, Long amount) {
        this.name = name;
        this.title = title;
        this.imageUrl = imageUrl;
        this.type = type;
        this.webtoonId = webtoonId;
        this.amount = 0L;
    }
    
    public void amountUpdate(Long amount) {
        this.amount += amount;
    }
}
