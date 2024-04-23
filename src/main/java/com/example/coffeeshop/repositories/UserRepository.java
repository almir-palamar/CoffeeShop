package com.example.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.coffeeshop.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
