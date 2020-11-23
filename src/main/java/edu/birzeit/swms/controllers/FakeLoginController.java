package edu.birzeit.swms.controllers;

import edu.birzeit.swms.security.UsernameAndPasswordAuthenticationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("")
@Api(tags = "Login Api to get authorized jwt token in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully authenticated"),
        @ApiResponse(code = 403, message = "Invalid Username or Password")
})
public class FakeLoginController {

    @ApiOperation("Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody UsernameAndPasswordAuthenticationRequest user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

}
