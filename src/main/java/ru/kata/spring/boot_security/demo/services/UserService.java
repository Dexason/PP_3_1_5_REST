package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
    User findOne(int id);
    void update(int id, User updatedUser);
    void delete(int id);
    User findByUsername(String username);
}
