package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.enums.UserRole;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.AreaMapper;
import edu.birzeit.swms.mappers.EmployeeMapper;
import edu.birzeit.swms.models.Employee;
import edu.birzeit.swms.repositories.EmployeeRepository;
import edu.birzeit.swms.services.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeDto> getEmployees() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeDtoList.add(employeeMapper.employeeToDto(employee)));
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        EmployeeDto employeeDto = employeeMapper.employeeToDto(employee);
        return employeeDto;
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        employee.setRole(UserRole.EMPLOYEE);
        employee.setPassword(passwordEncoder.encode("swms"+employee.getUsername()));
        employee.setEnabled(true);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = employeeMapper.employeeToDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhone(employeeDto.getPhone());
        employee.setAddress(employeeDto.getAddress());
        employee.setRole(UserRole.EMPLOYEE);
        employee.setEnabled(true);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = employeeMapper.employeeToDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Override
    public void deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    @Override
    public List<AreaDto> getAreasOfEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        List<AreaDto> areaDtoList = new ArrayList<>();
        employee.getAreaList().forEach(area -> areaDtoList.add(areaMapper.areaToDto(area)));
        return areaDtoList;
    }

}
