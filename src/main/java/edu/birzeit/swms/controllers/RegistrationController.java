package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.UserDto;
import edu.birzeit.swms.models.ConfirmationToken;
import edu.birzeit.swms.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void signUp(@RequestBody UserDto userDto) {
        userService.signUpUser(userDto);
    }

    @GetMapping("/sign-up/confirm")
    public void signUp(@RequestParam String token) {
        userService.confirmUser(token);
    }

}