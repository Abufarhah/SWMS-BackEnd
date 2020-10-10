package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.models.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    ModelMapper modelMapper;

    public Employee dtoToEmployee(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

    public EmployeeDto employeeToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

}
