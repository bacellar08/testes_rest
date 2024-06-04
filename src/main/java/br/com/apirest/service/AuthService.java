package br.com.apirest.service;

import br.com.apirest.model.dto.v1.security.AccountCredentialsDTO;
import br.com.apirest.model.dto.v1.security.TokenDTO;
import br.com.apirest.repository.UserRepository;
import br.com.apirest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    public AuthService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository repository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    public ResponseEntity<TokenDTO> signin(AccountCredentialsDTO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenDTO();

            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username not found");
            }
            return ResponseEntity.ok(tokenResponse);
        }
        catch (Exception e) {
           throw new BadCredentialsException("Invalid username or password");
        }
    }
}
