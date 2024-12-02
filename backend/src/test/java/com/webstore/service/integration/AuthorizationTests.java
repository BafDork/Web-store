package com.webstore.service.integration;

import com.webstore.helpers.TestUserHelper;
import com.webstore.model.Role;
import com.webstore.service.JwtService;
import com.webstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUserHelper testUserHelper;

    @Test
    public void testAccessProtectedEndpointWithValidToken() throws Exception {
        testUserHelper.createUserIfNotExists(Role.ROLE_USER);

        UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(testUserHelper.getEmail());
        String token = jwtService.generateToken(userDetails);

        mockMvc.perform(get("/protected-endpoint")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        testUserHelper.deleteUserIfExists();
    }

    @Test
    public void testAccessProtectedEndpointWithInvalidToken() throws Exception {
        String invalidToken = "invalid.token.string";

        mockMvc.perform(get("/protected-endpoint")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAccessProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/protected-endpoint"))
                .andExpect(status().isForbidden());
    }
}