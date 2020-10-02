package edu.birzeit.swms.controllers;

import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/citizens")
@Api(tags = "Operations related to citizens in SWMS")
public class CitizenController {
}
