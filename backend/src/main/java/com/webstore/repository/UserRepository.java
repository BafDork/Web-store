package com.webstore.repository;

import com.webstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по email.
     *
     * @param email email пользователя
     * @return объект пользователя
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверка существования пользователя по email.
     *
     * @param email email пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByEmail(String email);
}