package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepositories userRepositories, PasswordEncoder passwordEncoder) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepositories.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findOne(int id) {
        Optional<User> foundUser = userRepositories.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        userRepositories.save(updatedUser);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepositories.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userRepositories.findByUsername(username);
    }


}
