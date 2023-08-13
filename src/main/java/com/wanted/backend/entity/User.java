package com.wanted.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanted.backend.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity(name = "users")
public class User extends BaseTimeEntity {
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

    public static User of(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
