package com.wanted.backend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanted.backend.domain.member.entity.role.Role;
import com.wanted.backend.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Setter
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    @Setter
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }

    public Member encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
}
