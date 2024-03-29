package org.example.hpwe.validators;

import org.example.hpwe.forms.profile.PersonalChangeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonalChangeFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PersonalChangeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonalChangeForm form = (PersonalChangeForm) target;
    }
}
