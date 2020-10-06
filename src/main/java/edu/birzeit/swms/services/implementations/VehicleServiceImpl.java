package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.VehicleDto;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.VehicleMapper;
import edu.birzeit.swms.models.Vehicle;
import edu.birzeit.swms.repositories.VehicleRepository;
import edu.birzeit.swms.services.VehicleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<VehicleDto> getVehicles() {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicle -> vehicleDtoList.add(vehicleMapper.vehicleToDto(vehicle)));
        return vehicleDtoList;
    }

    @Override
    public VehicleDto getVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        VehicleDto vehicleDto = vehicleMapper.vehicleToDto(vehicle);
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
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        vehicle.setNumber(vehicleDto.getNumber());
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        VehicleDto savedVehicleDto = vehicleMapper.vehicleToDto(savedVehicle);
        return savedVehicleDto;
    }

    @Override
    public void deleteVehicle(int id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Vehicle", "id", id);
        }
    }

}
