package org.example.hpwe.validators;

import org.example.hpwe.forms.profile.AccountChangeForm;
import org.example.hpwe.service.authentication.AuthenticationUserService;
import org.example.hpwe.service.userservice.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@Slf4j
public class AccountChangeFormValidator implements Validator {
    private final UserService userService;
    private final AuthenticationUserService authenticationUserService;
    public AccountChangeFormValidator(UserService userService, AuthenticationUserService authenticationUserService) {
        this.userService = userService;
        this.authenticationUserService = authenticationUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountChangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountChangeForm form = (AccountChangeForm) target;

        if (!authenticationUserService.getAuthenticationUser().getEmail().equals(form.getEmail())) {
            if (Objects.nonNull(userService.findByEmail(form.getEmail()))) {
                errors.rejectValue("email", "error.account.email.exists");
            }
        }

        if (!authenticationUserService.getAuthenticationUser().getName().equals(form.getName())) {
            if (Objects.nonNull(userService.findByName(form.getName()))) {
                errors.rejectValue("name", "error.account.name.exists");
            }
        }

        if (StringUtils.isBlank(form.getName())) {
            errors.rejectValue("name", "error.account.name.empty");
        }

        if (StringUtils.isBlank(form.getEmail())) {
            errors.rejectValue("email", "error.account.email.empty");
        }
    }
}
