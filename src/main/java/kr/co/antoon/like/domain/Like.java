package kr.co.antoon.like.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
