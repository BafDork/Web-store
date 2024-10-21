package com.webstore.authentication;

import com.webstore.exceptions.InvalidTokenException;
import com.webstore.helpers.TestUserHelper;
import com.webstore.model.Role;
import com.webstore.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtServiceTests {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TestUserHelper testUserHelper;


    @Test
    public void testGenerateAndValidateToken() {
        UserDetails userDetails = testUserHelper.createMockUser(Role.ROLE_USER);

        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testInvalidToken() {
        UserDetails userDetails = testUserHelper.createMockUser(Role.ROLE_USER);
        String invalidToken = "invalid.token.string";

        InvalidTokenException exception = assertThrows(
                InvalidTokenException.class,
                () -> jwtService.isTokenValid(invalidToken, userDetails)
        );
    }

    @Test
    public void testExtractUsernameFromToken() {
        UserDetails userDetails = testUserHelper.createMockUser(Role.ROLE_USER);
        String token = jwtService.generateToken(userDetails);

        String usernameFromToken = jwtService.extractUserName(token);

        assertEquals(userDetails.getUsername(), usernameFromToken);
    }
}

