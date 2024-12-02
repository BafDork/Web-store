package com.webstore.service;

import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.exceptions.UserAlreadyExistsException;
import com.webstore.model.User;
import com.webstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Получение пользователя по ID.
     *
     * @param userId ID пользователя
     * @return найденный пользователь
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с ID: " + userId));
    }

    /**
     * Сохранение пользователя.
     *
     * @param user пользователь для сохранения
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Создание нового пользователя.
     *
     * @param user новый пользователь
     * @return созданный пользователь
     * @throws UserAlreadyExistsException если email уже существует
     */
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return save(user);
    }

    /**
     * Получение пользователя по email.
     *
     * @param email email пользователя
     * @return найденный пользователь
     * @throws UsernameNotFoundException если пользователь не найден
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с email: " + email));
    }

    /**
     * Удаление пользователя по email.
     *
     * @param email email пользователя для удаления
     */
    public void deleteByEmail(String email) {
        User user = getByEmail(email);
        userRepository.delete(user);
    }

    /**
     * Получение пользователя для Spring Security.
     *
     * @return UserDetailsService
     */
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Получение текущего аутентифицированного пользователя.
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }
}
