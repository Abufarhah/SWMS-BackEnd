package edu.birzeit.swms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmationController {

    @GetMapping("/confirm")
    public String greeting(@RequestParam String token, Model model) {
//        String response = "<html><head></head><body><a href=\"http://swms.ga/sign-up/confirm?token=" + token + "\"" +
//                ">activate your account</a></body></html>";
        model.addAttribute("token",token);
        return "confirm";

    }

}
