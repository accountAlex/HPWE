package org.example.hpwe.controllers;

import org.example.hpwe.util.RedirectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SingInController {

    @GetMapping("/sign-in")
    public ModelAndView login() {
        return new ModelAndView("sign-in");
    }

    @PostMapping("/sign-in")
    public ModelAndView loginAction() {
        return RedirectUtil.redirect("/");
    }
}
