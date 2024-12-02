package com.webstore.service.unit;

import com.webstore.dto.request.SignInRequestDTO;
import com.webstore.dto.request.SignUpRequestDTO;
import com.webstore.dto.response.JwtResponseDTO;
import com.webstore.model.User;
import com.webstore.service.AuthenticationService;
import com.webstore.service.JwtService;
import com.webstore.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private SignUpRequestDTO signUpRequest;
    private SignInRequestDTO signInRequest;
    private UserDetails userDetails;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        signUpRequest = new SignUpRequestDTO("John", "Doe", "john.doe@example.com", "password123");
        signInRequest = new SignInRequestDTO("john.doe@example.com", "password123");
        userDetails = mock(UserDetails.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    /**
     * Тестирует метод signUp, который должен зарегистрировать нового пользователя.
     */
    @Test
    void signUp_ShouldCreateUser_WhenValidRequest() {
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");

        authenticationService.signUp(signUpRequest);

        verify(userService, times(1)).create(any(User.class));
    }

    /**
     * Тестирует метод signIn, который должен аутентифицировать пользователя и возвращать JWT.
     */
    @Test
    void signIn_ShouldReturnJwt_WhenValidRequest() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        when(userService.userDetailsService()).thenReturn(mock(UserDetailsService.class));
        when(userService.userDetailsService().loadUserByUsername(signInRequest.getEmail())).thenReturn(userDetails);

        when(jwtService.generateToken(userDetails)).thenReturn("some-jwt-token");

        JwtResponseDTO result = authenticationService.signIn(signInRequest);

        assertNotNull(result);
        assertEquals("some-jwt-token", result.getToken());
    }

    /**
     * Тестирует метод signIn, который должен выбросить исключение при неверных данных.
     */
    @Test
    void signIn_ShouldThrowException_WhenAuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authenticationService.signIn(signInRequest));
    }
}