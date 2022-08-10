package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.vote.domain.vo.TopicCategory;
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
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TopicCategory topicCategory;

    private String tags;

    private String title;

    private LocalDateTime topicVoteTime;

    private Integer joinCount;

    private Boolean topicVoteStatus;

    private boolean topicCloseStatus;

    @Builder
    public Topic(TopicCategory topicCategory, String tags, String title, LocalDateTime topicVoteTime,
                 Integer joinCount, Boolean topicVoteStatus, boolean topicCloseStatus) {
        this.topicCategory = topicCategory;
        this.tags = tags;
        this.title = title;
        this.topicVoteTime = topicVoteTime;
        this.joinCount = joinCount;
        this.topicVoteStatus = topicVoteStatus;
        this.topicCloseStatus = topicCloseStatus;
    }

    public void updateJoinCount() {
        this.joinCount += 1;
    }

    public void changeVoteStatus(boolean topicVoteStatus) {
        this.topicVoteStatus = topicVoteStatus;
    }

    public void updateCloseStatus(boolean topicCloseStatus) {
        this.topicCloseStatus = topicCloseStatus;
    }
}
