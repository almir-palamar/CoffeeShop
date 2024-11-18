package com.example.coffeeshop.controllers.v1;

import com.example.coffeeshop.models.Coffee;
import com.example.coffeeshop.services.CoffeeService;
import com.example.coffeeshop.services.JwtService;
import com.example.coffeeshop.services.OrderService;
import com.example.coffeeshop.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebMVC focuses on spring MVC components,
// disables auto config and configure only relevant to MVC tests
// loading only necessary things

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoffeeController.class)
@AutoConfigureMockMvc
class CoffeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CoffeeService coffeeService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    // It is not unit test, this is integration test
    // because I am testing rest controller and it does integrate
    // some other classes such as Request

    // It depends on which layer of application I am testing
    // So if I am testing the controller than application expects auth

    @Test
    @WithMockUser
    @Disabled
    public void getCoffee() throws Exception {

        // Arrange
//        Coffee coffee = new Coffee("Espresso",
//                35,
//                7,
//                1.00f,
//                null);
//
//        User adminUser = new User("admin",
//                "admin",
//                "admin",
//                "admin@gmail.com",
//                passwordEncoder.encode("admin"),
//                RoleEnum.ADMIN);

        // here should be an authentication for admin user
        // in order to save the coffee

        // Act
        // here should be mapped into coffee model
//        coffeeService.save(new CoffeeRequest(), null);
//        RequestBuilder request = MockMvcRequestBuilders.get("/coffee");

        MvcResult result = mvc.perform(get("/api/v1/coffee"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();


    }
}