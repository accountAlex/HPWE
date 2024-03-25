package org.example.hpwe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {

    // Метод для отображения формы регистрации
    @GetMapping("/sign-up")
    public String showRegistrationForm() {
        return "sign-up"; // Возвращает имя HTML файла формы регистрации
    }

    // Метод для обработки данных формы регистрации
    @PostMapping("/sign-up")
    public ModelAndView registerUser(
            @RequestParam("form3Example1c") String name,
            @RequestParam("form3Example3c") String email,
            @RequestParam("form3Example4c") String password,
            @RequestParam("form2Example3c") boolean terms) {

        // Здесь должна быть логика регистрации пользователя, например, сохранение в базу данных

        // После регистрации перенаправляем на страницу успешной регистрации (например, "registration-success")
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration-success"); // Укажите имя вашего файла успешной регистрации
        modelAndView.addObject("name", name);

        return modelAndView;
    }
}
