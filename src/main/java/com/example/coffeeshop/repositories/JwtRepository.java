package com.example.coffeeshop.repositories;

import com.example.coffeeshop.models.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtRepository extends JpaRepository<JwtToken, Long> {

    public JwtToken findByToken(String token);

    @Query("""
            select t 
            from JwtToken t 
            inner join User u on t.user.id=u.id
            where u.id = :userId and t.revoked = false
            """)
    public List<JwtToken> findValidTokensByUserId(Long userId);

}
