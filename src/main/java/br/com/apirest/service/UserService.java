package br.com.apirest.service;

import br.com.apirest.model.User;
import br.com.apirest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService (UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Finding user by username: " + username);

        var user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;

    }
}
