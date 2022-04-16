package kr.co.antoon.user.domain;
import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import kr.co.antoon.user.domain.vo.Role;
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

    private String imageUrl;
    private String picture;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String accessToken;
    @Column(nullable = false)
    private String refreshToken;
    @Builder
    public User(String name, String email, String imageUrl, Role role, String refreshToken) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.refreshToken = refreshToken;
    }
    @Builder
    public User(String name, String email, String picture, String accessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public User update(String name, String imageUrl, String refreshToken) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
        this.refreshToken = refreshToken;
    }
}