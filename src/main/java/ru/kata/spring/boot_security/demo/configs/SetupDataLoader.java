package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserService userService;
    private final RoleService roleService;

    public SetupDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        Role roleUser = createRoleIfNotFound("ROLE_USER");
        createUserIfNotFound("admin", 30, "admin@mail.ru", "testpass", roleAdmin);
        createUserIfNotFound("user", 25, "user@mail.ru","test123", roleUser);
        alreadySetup = true;
    }
    @Transactional
    Role createRoleIfNotFound(String name) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleService.save(role);
        }
        return role;
    }
    @Transactional
    User createUserIfNotFound(String name, int age, String email, String password, Role role) {
        User user = userService.findByUsername(name);
        if (user == null) {
            user = new User(name, age, email, password, List.of(role));
            userService.save(user);
        }
        return user;
    }
}
