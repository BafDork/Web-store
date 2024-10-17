package com.webstore.authentication;

import com.webstore.helpers.TestUserHelper;
import com.webstore.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUserHelper testUserHelper;


    @Test
    public void testSignUp_UserDoesNotExist() throws Exception {
        String signUpRequest = String.format("{ \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                testUserHelper.getUsername(), testUserHelper.getEmail(), testUserHelper.getPassword());

        testUserHelper.deleteUserIfExists();

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequest))
                .andExpect(status().isOk());

        testUserHelper.deleteUserIfExists();
    }

    @Test
    public void testSignUp_UserDoesExist() throws Exception {
        String signUpRequest = String.format("{ \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                testUserHelper.getUsername(), testUserHelper.getEmail(), testUserHelper.getPassword());

        testUserHelper.createUserIfNotExists(Role.ROLE_USER);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequest))
                .andExpect(status().isConflict());

        testUserHelper.deleteUserIfExists();
    }

    @Test
    public void testSignIn_UserDoesNotExist() throws Exception {
        String signInRequest = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                testUserHelper.getUsername(), testUserHelper.getPassword());

        testUserHelper.deleteUserIfExists();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSignIn_UserDoesExist() throws Exception {
        String signInRequest = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                testUserHelper.getUsername(), testUserHelper.getPassword());

        testUserHelper.createUserIfNotExists(Role.ROLE_USER);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());

        testUserHelper.deleteUserIfExists();
    }
}

