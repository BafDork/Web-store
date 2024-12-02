package com.webstore.service.unit;

import com.webstore.model.User;
import com.webstore.model.Role;
import com.webstore.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "jwtSigningKey", "9afe53f9139941905e830f5096eba6fb45b27a3f6e72070a8d728a52536abf801ae58ad976d389fe8d92d78a997c3b8b2e76cc6800b364b3d52e9f4e473c5b095ef83a59bcce855c063aaa96f65de8ed55fb4d5a1f012af72a1b69f3067c93935a94cbab66a50417293cb0b144424e12781dd66095cba5ab13fa194de08314f0b7e91c71a79fdd4704b597bfe62a012c5b63e7b58d089642a6788f3d240b41cee94b77b922cba5369636355e3d96e66a4959f0b306ca38f6a5eaa89e416f2c950f88412f4922048bb46d761da7172121f1c5393e627899dc85f5ce08f0ba5266a364aea894262cab8d4c4338c2ee691ab32f41e26b759b1d813e1dc3c75981eb");
    }

    /**
     * Тестирует извлечение имени пользователя из токена.
     * Ожидается, что имя пользователя будет извлечено правильно.
     */
    @Test
    void extractUserName_ShouldReturnUserName_WhenTokenIsValid() {
        String userName = "test@example.com";
        String token = jwtService.generateToken(new User(1L, "Test", "User",
                "test@example.com", "password",  Role.ROLE_USER));

        String result = jwtService.extractUserName(token);

        assertEquals(userName, result);
    }

    /**
     * Тестирует проверку валидности токена.
     * Ожидается, что токен будет валиден, если он не истек и совпадает с данными пользователя.
     */
    @Test
    void isTokenValid_ShouldReturnTrue_WhenTokenIsValid() {
        UserDetails userDetails = new User(1L, "Test", "User",
                "test@example.com", "password",  Role.ROLE_USER);
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    /**
     * Тестирует проверку валидности токена, когда имя пользователя не совпадает.
     * Ожидается, что токен будет невалидным.
     */
    @Test
    void isTokenValid_ShouldReturnFalse_WhenUserNameDoesNotMatch() {
        UserDetails validUser = new User(1L, "Test", "User",
                "test@example.com", "password",  Role.ROLE_USER);
        UserDetails invalidUser = new User(1L, "Test2", "User2",
                "bad@example.com", "password",  Role.ROLE_USER);

        String token = jwtService.generateToken(validUser);

        boolean isValid = jwtService.isTokenValid(token, invalidUser);

        assertFalse(isValid);
    }

    /**
     * Тестирует получение оставшегося времени жизни токена.
     * Ожидается, что метод вернет корректное время до истечения.
     */
    @Test
    void getRemainingTime_ShouldReturnPositiveTime_WhenTokenIsNotExpired() {
        UserDetails userDetails = new User(1L, "Test", "User",
                "test@example.com", "password",  Role.ROLE_USER);
        String token = jwtService.generateToken(userDetails);

        long remainingTime = jwtService.getRemainingTime(token);

        assertTrue(remainingTime > 0);
    }

    /**
     * Тестирует извлечение данных из токена (проверка на срок истечения).
     * Ожидается, что оставшееся время будет равно EXPIRATION_TIME - 2 cек.
     */
    @Test
    void getRemainingTime_ShouldReturnRemainingTime_WhenTokenIsExpired() {
        UserDetails userDetails = new User(1L, "Test", "User",
                "test@example.com", "password", Role.ROLE_USER);

        String token = jwtService.generateToken(userDetails);

        long remainingTime = jwtService.getRemainingTime(token);
        long expirationTime = 100000 * 60 * 24;

        assertTrue(remainingTime >= expirationTime - 2000 && remainingTime <= expirationTime + 2000,
                "Expected remaining time to be close to " + expirationTime + " but was " + remainingTime);
    }
}
