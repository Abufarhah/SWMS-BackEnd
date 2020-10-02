package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getEmployees();

    EmployeeDto getEmployee(int id);

    EmployeeDto addEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(EmployeeDto employeeDto, int id);

    void deleteEmployee(int id);
}
