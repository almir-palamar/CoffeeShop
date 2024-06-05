package com.example.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.coffeeshop.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

}
