package kr.co.antoon.member.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImg;

    @Column
    private String refreshToken;

    @Builder
    public User(String name, String email, String profileImg, String refreshToken) {
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
        this.refreshToken = refreshToken;
    }

    public User update(String name, String profileImg) {
        this.name = name;
        this.profileImg = profileImg;

        return this;
    }
}
