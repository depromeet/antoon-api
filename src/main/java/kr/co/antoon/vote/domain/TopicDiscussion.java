package kr.co.antoon.vote.domain;

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
public class TopicDiscussion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long topicId;

    private Long userId;

    private int likeCount = 0;

    @Builder
    public TopicDiscussion(String content, Long topicId, Long userId) {
        this.content = content;
        this.topicId = topicId;
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
