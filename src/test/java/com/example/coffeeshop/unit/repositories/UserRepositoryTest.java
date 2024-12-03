package com.example.coffeeshop.unit.repositories;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import com.example.coffeeshop.enums.RoleEnum;
import com.example.coffeeshop.models.User;
import com.example.coffeeshop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class UserRepositoryTest extends CoffeeShopApplicationTests {

    @Container
    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("coffeeshop");

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByUsername() {
        User user = User.builder()
                .username("john")
                .email("john@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .role(RoleEnum.USER)
                .build();
        userRepository.saveAndFlush(user);

        User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(foundUser.getLastName()).isEqualTo(user.getLastName());
    }

    @Test
    void shouldReturnPageWithUsers() {
        User user = User.builder()
                .username("john")
                .email("john@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .role(RoleEnum.USER)
                .build();
        userRepository.saveAndFlush(user);
        int page = 0;
        int pageSize = 10;

        Page<User> allUsers = userRepository.findAll(PageRequest.of(page, pageSize));

        assertEquals(1, allUsers.getTotalPages());
        assertEquals(1, allUsers.getContent().size());
        assertThat(allUsers.getContent().getFirst().getUsername()).isEqualTo(user.getUsername());
    }
}