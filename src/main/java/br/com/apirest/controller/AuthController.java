package br.com.apirest.controller;

import br.com.apirest.model.dto.v1.security.AccountCredentialsDTO;
import br.com.apirest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and returns its token")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsDTO data) {

        if (isParamsNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        var token = authService.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refreshs token for authenticated user and returns a token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {

        if (isParamsNullRefresh(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        return token;
    }

    private static boolean isParamsNullRefresh(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }

    private static boolean isParamsNull(AccountCredentialsDTO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
