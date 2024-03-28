package org.example.hpwe.controllers;


import org.example.hpwe.entity.User;
import org.example.hpwe.forms.SingUpForm;
import org.example.hpwe.service.userservice.UserService;
import org.example.hpwe.util.RedirectUtil;
import org.example.hpwe.validators.SignUpFormValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {
    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;

    @Autowired
    public SignUpController(
            UserService userService,
            SignUpFormValidator signUpFormValidator
    ) {
        this.userService = userService;
        this.signUpFormValidator = signUpFormValidator;
    }

    @InitBinder("singUpForm")
    public void setSingUpForm(WebDataBinder binder) {
        binder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public ModelAndView registration() {
        return new ModelAndView("sign-up")
                .addObject("signUpForm", new SingUpForm());
    }

    @PostMapping("/sign-up")
    public ModelAndView registrationAction(@ModelAttribute @Valid SingUpForm form, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("sign-up")
                    .addObject("signUpForm", new SingUpForm());
        }
        User user = userService.createUser(form);
        userService.save(user);

        return RedirectUtil.redirect("/sign-in");
    }
}
