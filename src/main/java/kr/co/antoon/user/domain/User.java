package kr.co.antoon.user.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.oauth.dto.TokenDto;
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
    private String picture;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;


    @Builder
    public User(String name, String email, String picture, String accessToken, String refreshToken) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }
}
