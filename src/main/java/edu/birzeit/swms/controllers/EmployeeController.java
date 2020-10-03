package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.services.EmployeeService;
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
@RequestMapping("/employees")
@Api(tags = "Operations related to employees in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto addEmployee(@Validated @RequestBody EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@Validated @RequestBody EmployeeDto employeeDto, @PathVariable int id) {
        return employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}/areas")
    public List<AreaDto> getAreasOfEmployee(@PathVariable int id) {
        return employeeService.getAreasOfEmployee(id);
    }
}
