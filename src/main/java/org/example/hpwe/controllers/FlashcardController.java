package org.example.hpwe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlashcardController {

    @GetMapping("/flashcards")
    public String showFlashcards() {
        return "flashcards";
    }
}