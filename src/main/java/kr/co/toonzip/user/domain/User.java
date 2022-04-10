package kr.co.toonzip.user.domain;

import kr.co.toonzip.common.entity.BaseEntity;
import kr.co.toonzip.user.domain.vo.ProviderType;
import kr.co.toonzip.user.domain.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    private String providerId;

    @Builder
    public User(String name, String email, String imageUrl, Role role, String password, ProviderType provider, String providerId) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }
}
