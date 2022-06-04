package kr.co.antoon.feedback.entity;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.feedback.entity.vo.Score;
import kr.co.antoon.feedback.entity.vo.Status;
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
public class Feedback extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Score score;

    private Status status;

    private Long userId;

    private Long adminId;

    private String answer;

    @Builder
    public Feedback(String content, Score score, Long userId) {
        this.content = content;
        this.score = score;
        this.userId = userId;
        this.status = Status.REGISTERED;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void proceed() {
        changeStatus(Status.PROCEED);
    }

    public void reply(String answer) {
        this.answer = answer;
        changeStatus(Status.COMPLETED);
    }
}