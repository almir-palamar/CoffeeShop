package com.example.coffeeshop.integration.controllers.v1;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoffeeControllerTest extends CoffeeShopApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Container
    @ServiceConnection
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0")
            .withPassword("test")
            .withUsername("test")
            .withDatabaseName("coffeeshop");

    @Test
    void shouldReturnAllCoffees() throws Exception {

        RequestBuilder request = get("/api/v1/coffees");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void onCreateShouldReturnCreatedIfAdminLoggedIn() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "coffeeImage",
                "espresso.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getClass().getResourceAsStream("images/espresso.jpg")
        );

        RequestBuilder request = multipart("/api/v1/coffees")
                .file(imageFile)
                .param("name", "Machiatto")
                .param("brewTime", "40")
                .param("caffeineGram", "9")
                .param("price", "1.40")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isCreated()).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Machiatto");
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void onCreateShouldThrowExceptionIfUserLoggedIn() throws Exception {

        MockMultipartFile fileImage = new MockMultipartFile(
                "coffeeImage",
                "espresso.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getClass().getResourceAsStream("images/espresso.jpg")
        );

        RequestBuilder request = multipart("/api/v1/coffees")
                .file(fileImage)
                .param("name", "Machiatto")
                .param("brewTime", "40")
                .param("caffeineGram", "9")
                .param("price", "1.40")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isForbidden()).andReturn();
    }
}