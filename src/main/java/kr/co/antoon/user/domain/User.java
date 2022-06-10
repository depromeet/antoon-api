package kr.co.antoon.user.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.user.domain.vo.Gender;
import kr.co.antoon.user.domain.vo.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Integer age;

    @Builder
    public User(String name, String email, String imageUrl, Gender gender, Role role, Integer age) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.role = role;
        this.age = age;
    }

    public static User buildUser(String name, String email, String imageUrl, Gender gender, Integer age) {
        return User.builder()
                .name(name)
                .email(email)
                .imageUrl(imageUrl)
                .gender(gender)
                .role(Role.USER)
                .age(age)
                .build();
    }

    public User update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public User updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    public void updateAge(Integer age) {
        this.age = age;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

}