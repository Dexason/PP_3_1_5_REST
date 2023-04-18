package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    public AdminController(UserService userService, ModelMapper modelMapper, UserValidator userValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> index() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    public User show(@PathVariable("id") Integer id) {
        return userService.findOne(id);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("users/edit/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable("id") Integer id) {
        userValidator.validate(userDTO, bindingResult);
        userService.update(id, convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
