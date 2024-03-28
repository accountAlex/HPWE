package org.example.hpwe.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class RedirectUtil {
    public static ModelAndView redirect(String path) {
        RedirectView view = new RedirectView();
        view.setUrl(path);
        view.setExposeModelAttributes(false);
        return new ModelAndView(view);
    }
}