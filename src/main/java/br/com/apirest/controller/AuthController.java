package br.com.apirest.controller;

import br.com.apirest.model.dto.v1.security.AccountCredentialsDTO;
import br.com.apirest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Authenticates a user and returns its token")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsDTO data) {

        if (isParamsNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        var token = authService.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        return token;
    }

    private static boolean isParamsNull(AccountCredentialsDTO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
