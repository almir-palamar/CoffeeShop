package com.example.coffeeshop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public JwtToken(String token, boolean revoked, User user) {
        this.token = token;
        this.revoked = revoked;
        this.user = user;
    }

    public JwtToken() {}

    public boolean isRevoked() {
        return revoked;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
