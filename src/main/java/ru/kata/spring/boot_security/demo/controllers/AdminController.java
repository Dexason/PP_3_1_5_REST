package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "show";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/users")
    public String add(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if(userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "", "Этот адрес электронной почты уже используется");
        }
        if(userService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "new";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        if(userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "", "Этот адрес электронной почты уже используется");
        }
        if(userService.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "edit";
        }
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String remove(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
