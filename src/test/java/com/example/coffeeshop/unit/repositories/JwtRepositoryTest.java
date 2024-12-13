package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.enums.RoleEnum;
import com.example.coffeeshop.models.JwtToken;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.JwtRepository;
import com.example.coffeeshop.repositories.UserRepository;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class JwtRepositoryTest extends CoffeeShopApplicationTests {

    @Container
    @ServiceConnection
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("coffeeShop");

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindJwtTokenByToken() {
        JwtToken jwtToken = JwtToken.builder()
                .id(1L)
                .token("eyJhbGJ9.eyJybTiJ9XSwic3ViIjo.39XcvB-E")
                .revoked(false)
                .user(null)
                .build();
        jwtRepository.saveAndFlush(jwtToken);

        JwtToken foundJwtToken = jwtRepository.findByToken(jwtToken.getToken()).orElseThrow();

        assertThat(foundJwtToken).isNotNull();
        assertThat(foundJwtToken.getToken()).isEqualTo(jwtToken.getToken());
    }

    @Test
    void shouldFindValidJwtTokenByUserId() {
        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .role(RoleEnum.USER)
                .build();

        JwtToken jwtToken = JwtToken.builder()
                .id(1L)
                .token("eyJhbGJ9.eyJybTiJ9XSwic3ViIjo.39XcvB-E")
                .revoked(false)
                .user(user)
                .build();

        userRepository.saveAndFlush(user);
        jwtRepository.saveAndFlush(jwtToken);

        List<JwtToken> foundJwtTokens = jwtRepository.findValidTokensByUserId(user.getId());

        assertThat(foundJwtTokens.size()).isEqualTo(1);
        assertThat(foundJwtTokens.getFirst().getToken()).isEqualTo(jwtToken.getToken());
    }
}