package org.example.hpwe.service.profileservice;

import org.example.hpwe.entity.User;
import org.example.hpwe.forms.profile.AccountChangeForm;
import org.example.hpwe.forms.profile.PasswordChangeForm;
import org.example.hpwe.forms.profile.PersonalChangeForm;
import org.example.hpwe.models.UserToProfileModel;
import org.example.hpwe.service.authentication.AuthenticationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final AuthenticationUserService authenticationUserService;
    private final PasswordEncoder encoder;

    @Autowired
    public ProfileServiceImpl(AuthenticationUserService authenticationUserService, PasswordEncoder encoder) {
        this.authenticationUserService = authenticationUserService;
        this.encoder = encoder;
    }

    @Override
    public UserToProfileModel getUserProfile(
            User user
    ){
        return new UserToProfileModel()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setRole(user.getRole())
                .setPhone(user.getPhone())
                .setFirstName(user.getFirstName())
                .setSurname(user.getSurname());
    }

    @Override
    public User updatePersonalInfo(PersonalChangeForm form) {
        return authenticationUserService.getAuthenticationUser()
                .setFirstName(form.getFirstName())
                .setSurname(form.getSurname())
                .setPhone(form.getPhone());
    }

    @Override
    public User updateSystemInfo(AccountChangeForm form) {
        return authenticationUserService.getAuthenticationUser()
                .setName(form.getName())
                .setEmail(form.getEmail());
    }

    @Override
    public User updatePassword(PasswordChangeForm form) {
        return authenticationUserService.getAuthenticationUser()
                .setPassword(encoder.encode(form.getNewPassword()));
    }
}
