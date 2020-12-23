package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.services.AreaService;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/areas")
@Api(tags = "Operations related to areas in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class AreaController {

    @Autowired
    AreaService areaService;

    @GetMapping
    @ApiOperation(value = "This API used to get all areas", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<AreaDto> getAreas() {
        return areaService.getAreas();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "This API used to get specific area based on id", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public AreaDto getArea(@PathVariable int id) {
        return areaService.getArea(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "This API used to add new area", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AreaDto addArea(@RequestBody AreaDto areaDto) {
        return areaService.addArea(areaDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "This API used to update area based on id", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public AreaDto updateArea(@RequestBody AreaDto areaDto, @PathVariable int id) {
        return areaService.updateArea(areaDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "This API used to delete area based on id", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteArea(@PathVariable int id) {
        areaService.deleteArea(id);
    }

    @GetMapping("/{id}/bins")
    @ApiOperation(value = "This API used to get all bins of specific area base on id", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<BinDto> getBinsOfArea(@PathVariable int id) {
        return areaService.getBinsOfArea(id);
    }

    @GetMapping("{areaId}/assign-bin/{binId}")
    @ApiOperation(value = "This API used to assign a bin to an area", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void assignBin(@PathVariable int areaId, @PathVariable int binId) {
        areaService.assignBin(areaId, binId);
    }

    @GetMapping("{areaId}/unassign-bin/{binId}")
    @ApiOperation(value = "This API used to unassign a bin from an area", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void unAssignBin(@PathVariable int areaId, @PathVariable int binId) {
        areaService.unAssignBin(areaId, binId);
    }

    @GetMapping("{areaId}/assign-employee/{employeeId}")
    @ApiOperation(value = "This API used to assign an employee to an area", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void assignEmployee(@PathVariable int areaId, @PathVariable int employeeId) {
        areaService.assignEmployee(areaId, employeeId);
    }

    @GetMapping("{areaId}/unassign-employee/{employeeId}")
    @ApiOperation(value = "This API used to unassign an employee from an area", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void unAssignEmployee(@PathVariable int areaId, @PathVariable int employeeId) {
        areaService.unAssignEmployee(areaId, employeeId);
    }

    @GetMapping("/{id}/employees")
    @ApiOperation(value = "This API used to get all employees from specific area based on id", authorizations = {@Authorization(value = "jwtToken")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<EmployeeDto> getEmployeesOfArea(@PathVariable int id) {
        return areaService.getEmployeesOfArea(id);
    }


}
