package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.util.List;


@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO)target;
        if(userService.findByEmail(userDTO.getEmail()) != null) {
            errors.rejectValue("email", "", "Этот адрес электронной почты уже используется");
        }
        if(userService.findByUsername(userDTO.getUsername()) != null) {
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
        if(errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorList = errors.getFieldErrors();
            for(FieldError error : errorList) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new UserCreateException(errorMsg.toString());
        }
    }
}
