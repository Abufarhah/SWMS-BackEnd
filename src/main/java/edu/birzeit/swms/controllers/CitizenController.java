package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.CitizenDto;
import edu.birzeit.swms.services.CitizenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/citizens")
@Api(tags = "Operations related to citizens in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class CitizenController {

    @Autowired
    CitizenService citizenService;

    @GetMapping
    public List<CitizenDto> getCitizens() {
        return citizenService.getCitizens();
    }

    @GetMapping("/{id}")
    public CitizenDto getCitizen(@PathVariable int id) {
        return citizenService.getCitizen(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitizenDto addCitizen(@Validated @RequestBody CitizenDto citizenDto) {
        return citizenService.addCitizen(citizenDto);
    }

    @PutMapping("/{id}")
    public CitizenDto updateCitizen(@Validated @RequestBody CitizenDto citizenDto, @PathVariable int id) {
        return citizenService.updateCitizen(citizenDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCitizen(@PathVariable int id) {
        citizenService.deleteCitizen(id);
    }

}
