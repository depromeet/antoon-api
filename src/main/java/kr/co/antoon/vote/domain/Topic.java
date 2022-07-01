package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.vote.domain.vo.TopicCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
