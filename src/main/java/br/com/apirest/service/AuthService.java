package br.com.apirest.service;

import br.com.apirest.model.dto.v1.security.AccountCredentialsDTO;
import br.com.apirest.model.dto.v1.security.TokenDTO;
import br.com.apirest.repository.UserRepository;
import br.com.apirest.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {

    Logger logger = Logger.getLogger(AuthService.class.getName());

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    public AuthService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository repository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsDTO data) {

        try {
            logger.info("Variable username: " + data.getUsername());
            var username = data.getUsername();
            logger.info("Variable password " + data.getPassword());
            var password = data.getPassword();
            logger.info("running authentication manager");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            logger.info("Entering repository");
            var user = repository.findByUsername(username);

            var tokenResponse = new TokenDTO();

            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username not found");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (BadCredentialsException e) {
            logger.info("Bad credentials: " + e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
