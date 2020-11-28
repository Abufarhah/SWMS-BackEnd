package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.UserDto;
import edu.birzeit.swms.services.UserService;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity signUp(@RequestBody UserDto userDto) {
        userService.signUpUser(userDto);
        return new ResponseEntity("You are signed up successfully!\nActivation link sent to you", HttpStatus.OK);
    }

    @GetMapping("/user")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public UserDto getUser() {
        return userService.getUser();
    }

}
