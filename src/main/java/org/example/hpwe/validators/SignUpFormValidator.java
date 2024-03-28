package org.example.hpwe.validators;

import org.example.hpwe.forms.SingUpForm;
import org.example.hpwe.service.authentication.AuthenticationUserService;
import org.example.hpwe.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class SignUpFormValidator implements Validator {

    private final UserService userService;
    private final AuthenticationUserService authenticationUserService;

    @Autowired
    public SignUpFormValidator(UserService userService, AuthenticationUserService authenticationUserService) {
        this.userService = userService;
        this.authenticationUserService = authenticationUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SingUpForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SingUpForm form = (SingUpForm) target;

        if (Objects.nonNull(userService.findByEmail(form.getEmail()))) {
            errors.rejectValue("email", "user.email.exist");
        }
    }
}
