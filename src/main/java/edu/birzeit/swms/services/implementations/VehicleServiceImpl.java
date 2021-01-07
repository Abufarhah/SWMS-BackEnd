package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.VehicleDto;
import edu.birzeit.swms.exceptions.ResourceAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.VehicleMapper;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Employee;
import edu.birzeit.swms.models.Vehicle;
import edu.birzeit.swms.repositories.EmployeeRepository;
import edu.birzeit.swms.repositories.VehicleRepository;
import edu.birzeit.swms.services.VehicleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<VehicleDto> getVehicles() {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicle -> {
            VehicleDto vehicleDto = vehicleMapper.vehicleToDto(vehicle);
            List<Integer> employees = vehicle.getEmployeeList().stream().map(employee ->
                    employee.getId()).collect(Collectors.toList());
            vehicleDto.setEmployees(employees);
            vehicleDtoList.add(vehicleDto);
        });
        return vehicleDtoList;
    }

    @Override
    public VehicleDto getVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "id", id));
        VehicleDto vehicleDto = vehicleMapper.vehicleToDto(vehicle);
        List<Integer> employees = vehicle.getEmployeeList().stream().map(employee ->
                employee.getId()).collect(Collectors.toList());
        vehicleDto.setEmployees(employees);
        return vehicleDto;
    }

    @Override
    public VehicleDto addVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.dtoToVehicle(vehicleDto);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        VehicleDto savedVehicleDto = vehicleMapper.vehicleToDto(savedVehicle);
        return savedVehicleDto;
    }

    @Override
    public VehicleDto updateVehicle(VehicleDto vehicleDto, int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "id", id));
        vehicle.setNumber(vehicleDto.getNumber());
        employeeRepository.findAllById(vehicleDto.getEmployees()).forEach(employee ->
                vehicle.getEmployeeList().add(employee));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        VehicleDto savedVehicleDto = vehicleMapper.vehicleToDto(savedVehicle);
        List<Integer> employees = vehicle.getEmployeeList().stream().map(employee ->
                employee.getId()).collect(Collectors.toList());
        savedVehicleDto.setEmployees(employees);
        return savedVehicleDto;
    }

    @Override
    public void deleteVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "id", id));
        vehicle.getEmployeeList().forEach(employee -> employee.setVehicle(null));
        vehicleRepository.deleteById(id);
    }

    @Override
    public void assignEmployee(int vehicleId, int employeeId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "id", vehicleId));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow((() ->
                new ResourceNotFoundException("Employee", "id", employeeId)));
        if (vehicle.getEmployeeList().contains(employee)) {
            throw new ResourceAssignedException("Vehicle", vehicleId, "Employee", employeeId);
        } else {
            employee.setVehicle(vehicle);
            employeeRepository.save(employee);
        }
    }

    @Override
    public void unAssignEmployee(int vehicleId, int employeeId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle", "id", vehicleId));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow((() ->
                new ResourceNotFoundException("Employee", "id", employeeId)));
        if (vehicle.getEmployeeList().contains(employee)) {
            employee.setVehicle(null);
            employeeRepository.save(employee);
        } else {
            throw new ResourceNotAssignedException("Vehicle", vehicleId, "Employee", employeeId);
        }
    }

}
