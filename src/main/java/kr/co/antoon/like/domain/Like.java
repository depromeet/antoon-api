package kr.co.antoon.like.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean flag = true;

    private Long userId;

    private Long discussionId;

    @Builder
    public Like(Long userId, Long discussionId) {
        this.userId = userId;
        this.discussionId = discussionId;
    }

    public Like update() {
        this.flag = !this.flag;
        return this;
    }
}
