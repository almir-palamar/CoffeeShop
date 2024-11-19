package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.CoffeeShopApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoffeeControllerTest extends CoffeeShopApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void onGetCoffeesShouldReturnCoffees() throws Exception {

        // Arrange
        RequestBuilder request = get("/api/v1/coffees");

        // Act/Assert
        mvc.perform(request)
                .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void onCreateCoffeeShouldReturnCreatedIfAdmin() throws Exception {
        // Arrange
        String newCoffee = """     
                {
                    "name": "Machiatto",
                    "brewTime": 40,
                    "caffeineGram": 9,
                    "price": 1.40
                }
        """;

        RequestBuilder request = post("/api/v1/coffees")
                .content(newCoffee)
                .contentType(MediaType.APPLICATION_JSON);

        // Act/Assert
        MvcResult result = mvc.perform(request)
                .andExpect(status().isCreated()).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Machiatto");
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void onCreateShouldThrowExceptionIfRegularUserLoggedIn() throws Exception {
        // Arrange
        String newCoffee = """     
                {
                    "name": "Machiatto",
                    "brewTime": 40,
                    "caffeineGram": 9,
                    "price": 1.40
                }
        """;

        RequestBuilder request = post("/api/v1/coffees")
                .content(newCoffee)
                .contentType(MediaType.APPLICATION_JSON);

        // Act/Assert
        mvc.perform(request)
                .andExpect(status().isForbidden()).andReturn();
    }
}