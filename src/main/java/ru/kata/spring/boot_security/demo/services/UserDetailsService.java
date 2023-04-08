package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepositories userRepositories;

    public UserDetailsService(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositories.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
