package org.example.hpwe.controllers;

import org.example.hpwe.entity.User;
import org.example.hpwe.forms.profile.AccountChangeForm;
import org.example.hpwe.forms.profile.PasswordChangeForm;
import org.example.hpwe.forms.profile.PersonalChangeForm;
import org.example.hpwe.service.authentication.AuthenticationUserService;
import org.example.hpwe.service.profileservice.ProfileService;
import org.example.hpwe.service.userservice.UserService;
import org.example.hpwe.util.RedirectUtil;
import org.example.hpwe.validators.AccountChangeFormValidator;
import org.example.hpwe.validators.PasswordChangeFormValidator;
import org.example.hpwe.validators.PersonalChangeFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProfileController {
    private final AuthenticationUserService authenticationUserService;
    private final ProfileService profileService;
    private final UserService userService;
    private final PasswordChangeFormValidator passwordChangeFormValidator;
    private final AccountChangeFormValidator accountChangeFormValidator;
    private final PersonalChangeFormValidator personalChangeFormValidator;

    @Autowired
    public ProfileController(
            AuthenticationUserService authenticationUserService,
            ProfileService profileService,
            UserService userService,
            PasswordChangeFormValidator passwordChangeFormValidator,
            AccountChangeFormValidator accountChangeFormValidator,
            PersonalChangeFormValidator personalChangeFormValidator
    ) {
        this.authenticationUserService = authenticationUserService;
        this.profileService = profileService;
        this.userService = userService;
        this.passwordChangeFormValidator = passwordChangeFormValidator;
        this.accountChangeFormValidator = accountChangeFormValidator;
        this.personalChangeFormValidator = personalChangeFormValidator;
    }

    @InitBinder("passwordChangeForm")
    public void passwordChangeForm(WebDataBinder binder) {
        binder.addValidators(passwordChangeFormValidator);
    }

    @InitBinder("accountChangeForm")
    public void accountChangeForm(WebDataBinder binder) {
        binder.addValidators(accountChangeFormValidator);
    }

    @InitBinder("personalChangeForm")
    public void personalChangeForm(WebDataBinder binder) {
        binder.addValidators(personalChangeFormValidator);
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        User user = authenticationUserService.getAuthenticationUser();
        return new ModelAndView("profile")
                .addObject("authUser", profileService.getUserProfile(user));
    }

    @GetMapping("/settings/personal")
    public ModelAndView personalInfoGet() {
        User user = authenticationUserService.getAuthenticationUser();
        return new ModelAndView("personal-info")
                .addObject("personalChangeForm", new PersonalChangeForm())
                .addObject("authUser", profileService.getUserProfile(user));
    }

    @PostMapping("/settings/personal")
    public ModelAndView personalInfoPost(
            @ModelAttribute @Valid PersonalChangeForm updateInfoForm,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            User user = authenticationUserService.getAuthenticationUser();
            return new ModelAndView("personal-info")
                    .addObject("personalChangeForm", new PersonalChangeForm())
                    .addObject("authUser", profileService.getUserProfile(user));
        }
        User user = profileService.updatePersonalInfo(updateInfoForm);
        userService.save(user);

        return RedirectUtil.redirect("/settings/personal");
    }

    @GetMapping("/settings/account")
    public ModelAndView accountInfoGet() {
        User user = authenticationUserService.getAuthenticationUser();
        return new ModelAndView("account-info")
                .addObject("accountChangeForm", new AccountChangeForm())
                .addObject("authUser", profileService.getUserProfile(user));
    }

    @PostMapping("/settings/account")
    public ModelAndView accountInfoPost(
            @ModelAttribute @Valid AccountChangeForm systemInfoForm,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            User user = authenticationUserService.getAuthenticationUser();
            return new ModelAndView("account-info")
                    .addObject("accountChangeForm", new AccountChangeForm())
                    .addObject("authUser", profileService.getUserProfile(user));
        }
        User user = profileService.updateSystemInfo(systemInfoForm);
        authenticationUserService.saveUserAndUpdateSession(user);

        return RedirectUtil.redirect("/settings/account");
    }

    @GetMapping("/settings/system")
    public ModelAndView passwordChangeGet() {
        return new ModelAndView("password-change")
                .addObject("passwordChangeForm", new PasswordChangeForm());
    }

    @PostMapping("/settings/system")
    public ModelAndView passwordChangePost(
            @ModelAttribute @Valid PasswordChangeForm passwordChangeForm,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return new ModelAndView("password-change")
                    .addObject("passwordChangeForm", new PasswordChangeForm());
        }
        User user = profileService.updatePassword(passwordChangeForm);
        userService.save(user);

        return RedirectUtil.redirect("/settings/system");
    }

    @PostMapping("/settings/account/delete")
    public ModelAndView deleteUserPost(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        authenticationUserService.deleteUserAndCloseSession(request, response);

        return RedirectUtil.redirect("/sign-up");
    }
}