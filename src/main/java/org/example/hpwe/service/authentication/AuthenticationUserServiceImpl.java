package org.example.hpwe.service.authentication;

import org.example.hpwe.entity.User;
import org.example.hpwe.entity.Role;
import org.example.hpwe.service.principalservice.UserPrincipalImpl;
import org.example.hpwe.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationUserServiceImpl implements UserDetailsService, AuthenticationUserService {
    private final UserService userService;

    public AuthenticationUserServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> role = getGrantedAuthorities(user);

        return new UserPrincipalImpl(user.getId(), user.getName(), user.getEmail(), user.getPassword(), role, user.isActive());
    }

    @Override
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(authentication.getName());
    }

    @Override
    public List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(user.isActive() ? user.getRole() : Role.BANNED.getAuthority()));
        return role;
    }

    @Override
    public void saveUserAndUpdateSession(User user) {
        if (user == null) {
            throw new IllegalArgumentException("'user' не может быть null ");
        }

        try {
            userService.save(user);
            UserDetails updatedUserDetails = loadUserByUsername(user.getEmail());

            if (updatedUserDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new IllegalArgumentException("Не удалось обновить контекст безопасности для пользователя: " + user.getEmail());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка во время обновления сессии: ", e);
        }
    }

    @Override
    public void deleteUserAndCloseSession(HttpServletRequest request, HttpServletResponse response) {
        User authenticationUser = getAuthenticationUser();
        if (authenticationUser == null || request == null || response == null) {
            throw new IllegalArgumentException("Параметры не могут быть null");
        }

        try {
            userService.delete(authenticationUser);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(null);
                SecurityContextHolder.clearContext();

                SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
                logoutHandler.setInvalidateHttpSession(true);
                logoutHandler.setClearAuthentication(true);
                logoutHandler.logout(request, response, null);
            } else {
                throw new IllegalArgumentException("Пользователь не аутентифицирован");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка во время удаления сессии: ", e);
        }
    }
}