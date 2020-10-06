package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.VehicleDto;
import edu.birzeit.swms.services.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/vehicles")
@Api(tags = "Operations related to vehicles in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping
    public List<VehicleDto> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping("/{id}")
    public VehicleDto getVehicle(@PathVariable int id) {
        return vehicleService.getVehicle(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto addVehicle(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.addVehicle(vehicleDto);
    }

    @PutMapping("/{id}")
    public VehicleDto updateVehicle(@RequestBody VehicleDto vehicleDto, @PathVariable int id) {
        return vehicleService.updateVehicle(vehicleDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
    }

}
