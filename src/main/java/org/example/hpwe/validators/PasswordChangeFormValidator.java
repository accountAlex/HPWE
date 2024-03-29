package org.example.hpwe.validators;


import org.example.hpwe.forms.profile.PasswordChangeForm;
import org.example.hpwe.service.authentication.AuthenticationUserServiceImpl;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@Slf4j
public class PasswordChangeFormValidator implements Validator {
    private final AuthenticationUserServiceImpl authenticationUserService;
    private final PasswordEncoder encoder;

    @Autowired
    public PasswordChangeFormValidator(AuthenticationUserServiceImpl authenticationUserService, PasswordEncoder encoder) {
        this.authenticationUserService = authenticationUserService;
        this.encoder = encoder;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeForm form = (PasswordChangeForm) target;

        if (!encoder.matches(form.getOldPassword(), authenticationUserService.getAuthenticationUser().getPassword())) {
            errors.rejectValue("oldPassword", "error.password.old.invalid");
        }

        if (!encoder.matches(form.getConfirmPassword(), encoder.encode(form.getNewPassword()))) {
            errors.rejectValue("confirmPassword", "error.password.new.mismatch");
        }

        if (StringUtils.isBlank(form.getOldPassword())) {
            errors.rejectValue("oldPassword", "error.password.old.empty");
        }

        if (StringUtils.isBlank(form.getNewPassword())) {
            errors.rejectValue("newPassword", "error.password.new.empty");
        }

        if (StringUtils.isBlank(form.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "error.password.confirm.empty");
        }
    }
}
