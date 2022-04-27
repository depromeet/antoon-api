package kr.co.antoon.discussion.domain;

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
public class Discussion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long webtoonId;

    private Long memberId;

    private int likeCount = 0;

    @Builder
    public Discussion(String content, Long webtoonId, Long memberId) {
        this.content = content;
        this.webtoonId = webtoonId;
        this.memberId = memberId;
    }

    public void update(String content) {
        this.content = content;
    }

    public void updateLikeCount(Boolean flag) {
        if(flag) {
            this.likeCount += 1;
        } else if(likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}