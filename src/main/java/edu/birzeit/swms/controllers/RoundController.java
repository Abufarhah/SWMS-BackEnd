package edu.birzeit.swms.controllers;


import edu.birzeit.swms.dtos.RoundDto;
import edu.birzeit.swms.enums.RoundStatus;
import edu.birzeit.swms.services.RoundService;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/rounds")
@Api(tags = "Operations related to rounds in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class RoundController {

    @Autowired
    RoundService roundService;

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<RoundDto> getRounds() {
        return roundService.getRounds();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RoundDto getRound(@PathVariable int id) {
        return roundService.getRound(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public RoundDto addRound(@RequestBody RoundDto roundDto) {
        return roundService.addRound(roundDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public RoundDto updateRound(@RequestBody RoundDto roundDto, @PathVariable int id) {
        return roundService.updateRound(roundDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public void deleteRound(@PathVariable int id) {
        roundService.deleteRound(id);
    }

    @PutMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public RoundDto updateRoundStatus(@RequestParam RoundStatus status, int id) {
        return roundService.updateRoundStatus(status, id);
    }

}
