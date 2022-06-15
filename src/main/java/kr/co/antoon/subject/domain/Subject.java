package kr.co.antoon.subject.domain;

import kr.co.antoon.subject.domain.vo.SubjectType;
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
public class Subject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    private Long webtoonId;

    private Long amount;

    @Builder
    public Subject(String name, String title, String imageUrl, SubjectType type, Long webtoonId) {
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
