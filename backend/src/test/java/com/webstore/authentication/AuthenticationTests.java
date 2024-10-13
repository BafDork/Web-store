package com.webstore.authentication;

import com.webstore.model.Role;
import com.webstore.model.User;
import com.webstore.service.UserService;
import lombok.var;
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
    private UserService userService;

    private final String username = "test_user";
    private final String email = "test_user@mail.com";
    private final String password = "password123";


    private void createUserIfNotExists() {
        try {
            userService.getByUsername(username);
            var user = User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(Role.ROLE_USER)
                    .build();
            userService.save(user);
        } catch (Exception ignored) {
        }
    }

    private void deleteUserIfExists() {
        try {
            userService.getByUsername(username);
            userService.deleteByUsername(username);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testSignUp_UserDoesNotExist() throws Exception {
        String signUpRequest = String.format("{ \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                username, email, password);

        deleteUserIfExists();

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequest))
                .andExpect(status().isOk());

        deleteUserIfExists();
    }

    @Test
    public void testSignUp_UserDoesExist() throws Exception {
        String signUpRequest = String.format("{ \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                username, email, password);

        createUserIfNotExists();

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpRequest))
                .andExpect(status().isConflict());

        deleteUserIfExists();
    }

    @Test
    public void testSignIn_UserDoesNotExist() throws Exception {
        String signInRequest = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                username, password);

        deleteUserIfExists();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInRequest))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testSignIn_UserDoesExist() throws Exception {
        String signInRequest = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }",
                username, password);

        createUserIfNotExists();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());

        deleteUserIfExists();
    }

}
