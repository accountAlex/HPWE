package org.example.hpwe.service.authentication;

import org.example.hpwe.entity.User;
import org.example.hpwe.forms.profile.AccountChangeForm;
import org.example.hpwe.models.UserToProfileModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AuthenticationUserService {
    User getAuthenticationUser();
    List<GrantedAuthority> getGrantedAuthorities(User user);
    void deleteUserAndCloseSession(HttpServletRequest request, HttpServletResponse response);
    void saveUserAndUpdateSession(User user);
}
