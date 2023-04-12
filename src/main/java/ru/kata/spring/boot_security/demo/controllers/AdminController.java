package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        User admin = userService.findByUsername(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping()
    public String add(@ModelAttribute("user") @Valid User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
