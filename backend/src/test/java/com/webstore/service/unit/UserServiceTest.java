package com.webstore.service.unit;

import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.exceptions.UserAlreadyExistsException;
import com.webstore.model.User;
import com.webstore.repository.UserRepository;
import com.webstore.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Тестирует получение пользователя по ID, если пользователь найден.
     * Ожидается, что продукт будет найден по ID и возвращен.
     */
    @Test
    void testGetUserById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Тестирует получение пользователя по ID, если пользователь не найден.
     * Ожидается выбрасывание исключения ResourceNotFoundException с соответствующим сообщением.
     */
    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(1L));

        assertEquals("Пользователь не найден с ID: 1", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Тестирует создание пользователя, если email уже существует.
     * Ожидается выбрасывание исключения UserAlreadyExistsException с соответствующим сообщением.
     */
    @Test
    void testCreateUser_UserAlreadyExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userService.create(user));

        assertEquals("Пользователь с адресом test@example.com уже существует", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    /**
     * Тестирует создание нового пользователя, если email уникален.
     * Ожидается успешное создание пользователя с возвратом сохраненного объекта.
     */
    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Тестирует получение пользователя по email, если пользователь найден.
     * Ожидается, что пользователь будет найден по email и возвращен.
     */
    @Test
    void testGetByEmail_UserFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.getByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    /**
     * Тестирует получение пользователя по email, если пользователь не найден.
     * Ожидается выбрасывание исключения UsernameNotFoundException с соответствующим сообщением.
     */
    @Test
    void testGetByEmail_UserNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.getByEmail(user.getEmail()));

        assertEquals("Пользователь не найден с email: test@example.com", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    /**
     * Тестирует удаление пользователя по email, если пользователь найден.
     * Ожидается, что пользователь будет удален.
     */
    @Test
    void testDeleteByEmail_UserExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.deleteByEmail(user.getEmail());

        verify(userRepository, times(1)).delete(user);
    }

    /**
     * Тестирует удаление пользователя по email, если пользователь не найден.
     * Ожидается выбрасывание исключения UsernameNotFoundException с соответствующим сообщением и отсутствие вызова delete.
     */
    @Test
    void testDeleteByEmail_UserNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.deleteByEmail(user.getEmail()));

        assertEquals("Пользователь не найден с email: test@example.com", exception.getMessage());

        verify(userRepository, times(0)).delete(any(User.class));
    }

    /**
     * Тестирует получение текущего аутентифицированного пользователя.
     * Ожидается, что текущий аутентифицированный пользователь будет найден по email и возвращен.
     */
    @Test
    void testGetCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}
