package com.udacity.jwdnd.course1.cloudstorage.validator;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;


@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (Objects.nonNull(userRepository.findByUsername(user.getUsername()))) {
            errors.reject("Username already exist!", "Username already exist!");
        }

    }
}
