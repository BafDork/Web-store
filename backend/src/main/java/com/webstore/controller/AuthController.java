package com.webstore.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webstore.dto.response.JwtResponseDTO;
import com.webstore.dto.request.SignInRequestDTO;
import com.webstore.dto.request.SignUpRequestDTO;
import com.webstore.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestDTO request) {
        authenticationService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public JwtResponseDTO signIn(@RequestBody @Valid SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }

}