package kr.co.toonzip.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String refreshToken;

    public RefreshToken(Long id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
