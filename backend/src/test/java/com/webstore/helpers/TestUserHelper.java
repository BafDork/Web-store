package com.webstore.helpers;

import com.webstore.model.Role;
import com.webstore.model.User;
import com.webstore.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TestUserHelper {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Getter
    private final String firtsName = "User";
    @Getter
    private final String lastName = "Userov";
    @Getter
    private final String email = "test_user@mail.com";
    @Getter
    private final String password = "password123";

    public UserDetails createMockUser(Role role) {
        return new org.springframework.security.core.userdetails.User(
                email,
                passwordEncoder.encode(password),
                Collections.singletonList(new SimpleGrantedAuthority(role.name()))
        );
    }

    public boolean isUserExists() {
        try {
            userService.getByEmail(email);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    public void createUserIfNotExists(Role role) {
        if (!isUserExists()) {
            var user = User.builder()
                    .firstName(firtsName)
                    .lastName(lastName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();
            userService.save(user);
        }
    }

    public void deleteUserIfExists() {
        if (isUserExists()) {
            userService.deleteByEmail(email);
        }
    }
}
