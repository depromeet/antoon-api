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
public class TopicDiscussionLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status = true;

    private Long userId;

    private Long topicCommentId;

    @Builder
    public TopicDiscussionLike(Long userId, Long commentId) {
        this.userId = userId;
        this.topicCommentId = commentId;
    }

    public TopicDiscussionLike update() {
        this.status = !this.status;
        return this;
    }

}
