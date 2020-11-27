package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.UserDto;
import edu.birzeit.swms.models.ConfirmationToken;
import edu.birzeit.swms.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("")
@Api(tags = "Operations related to signing-up in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserDto userDto) {
        userService.signUpUser(userDto);
        return new ResponseEntity("You are signed up successfully!\nActivation link sent to you", HttpStatus.OK);
    }

    @GetMapping("/sign-up/confirm")
    public ResponseEntity signUp(@RequestParam String token) {
        userService.confirmUser(token);
        return new ResponseEntity("Your account activated successfully", HttpStatus.OK);
    }

    @GetMapping("/user")
    public UserDto getUser() {
        return userService.getUser();
    }

}
