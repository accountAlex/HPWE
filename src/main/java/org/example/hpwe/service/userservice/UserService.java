package org.example.hpwe.service.userservice;

import org.example.hpwe.entity.User;
import org.example.hpwe.forms.SingUpForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    User findByEmail(String email);
    void save(User user);
    User createUser(SingUpForm form);
    void delete(User user);
    User findByName(String name);
}
