package com.example.coffeeshop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coffees")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private Integer brewTime;
    private Integer caffeineGram;
    private Float price;
    private String imagePath;
    @CreatedBy
    private Long createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private Long lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Coffee(String name, Integer brewTime, Integer caffeineGram, Float price, String imagePath) {
        this.name = name;
        this.brewTime = brewTime;
        this.caffeineGram = caffeineGram;
        this.price = price;
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coffee coffee = (Coffee) obj;
        return Objects.equals(name, coffee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
