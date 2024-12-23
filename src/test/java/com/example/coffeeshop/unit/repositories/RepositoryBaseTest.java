package com.example.coffeeshop.unit.repositories;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // force DataJpaTest to use MySQLContainer
@Transactional(propagation = Propagation.NOT_SUPPORTED)                      // enables to see changes in db
public abstract class RepositoryBaseTest {

    @ServiceConnection
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("coffeeShop");


    // make mySQLContainer as a singleton instead of recreating it before each test
    static {
        mySQLContainer.start();
    }

}
