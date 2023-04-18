package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
    User findOne(Integer id);
    void update(Integer id, User updatedUser);
    void delete(Integer id);
    User findByUsername(String username);

    User findByEmail(String email);
}
