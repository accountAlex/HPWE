package org.example.hpwe.service.userservice;

import org.example.hpwe.entity.User;
import org.example.hpwe.entity.Role;
import org.example.hpwe.forms.SingUpForm;
import org.example.hpwe.repository.UserRepository;
import org.example.hpwe.service.authentication.AuthenticationUserService;
import org.example.hpwe.service.authentication.AuthenticationUserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User createUser(SingUpForm form) {
        return new User()
                .setEmail(form.getEmail())
                .setPassword(encoder.encode(form.getPassword()))
                .setName(StringUtils.substringBefore(form.getEmail(), "@"))
                .setActive(true)
                .setRole(Role.USER.getAuthority());
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
