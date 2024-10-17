package com.webstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected-endpoint")
public class ProtectedEndpointController {

    @GetMapping
    public String accessProtectedEndpoint() {
        return "Access granted to protected endpoint!";
    }
}
