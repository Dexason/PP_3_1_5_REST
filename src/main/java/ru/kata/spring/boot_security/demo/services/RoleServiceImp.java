package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepositories;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepositories roleRepositories;

    public RoleServiceImp(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepositories.save(role);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        return roleRepositories.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepositories.findByName(name);
    }
}
