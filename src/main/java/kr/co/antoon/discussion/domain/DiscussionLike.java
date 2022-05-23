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
public class DiscussionLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status = true;

    private Long userId;

    private Long discussionId;

    @Builder
    public DiscussionLike(Long userId, Long discussionId) {
        this.userId = userId;
        this.discussionId = discussionId;
    }

    public DiscussionLike update() {
        this.status = !this.status;
        return this;
    }
}