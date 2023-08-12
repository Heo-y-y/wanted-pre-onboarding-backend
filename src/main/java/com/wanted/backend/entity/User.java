package com.wanted.backend.entity;

import lombok.Setter;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Setter
    private String email;
    @Column(nullable = false)
    @Setter
    private String password;

    public static User of(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
