package com.webstore.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webstore.dto.JwtAuthenticationDTO;
import com.webstore.dto.SignInRequestDTO;
import com.webstore.dto.SignUpRequestDTO;
import com.webstore.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationDTO signUp(@RequestBody @Valid SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationDTO signIn(@RequestBody @Valid SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }

}