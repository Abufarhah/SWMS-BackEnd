package edu.birzeit.swms.controllers;

import edu.birzeit.swms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmationController {

    @Autowired
    UserService userService;

    @GetMapping("/confirm")
    public String greeting(@RequestParam String token, Model model) {
        model.addAttribute("token",token);
        return "confirm";
    }

    @GetMapping("/sign-up/confirm")
    public String signUp(@RequestParam String token) {
        userService.confirmUser(token);
        return "activated";
    }

}
