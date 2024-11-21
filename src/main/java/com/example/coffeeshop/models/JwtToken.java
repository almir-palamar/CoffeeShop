package com.example.coffeeshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

}
