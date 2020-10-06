package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.VehicleDto;
import edu.birzeit.swms.models.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    @Autowired
    ModelMapper modelMapper;

    public Vehicle dtoToVehicle(VehicleDto vehicleDto) {
        return modelMapper.map(vehicleDto, Vehicle.class);
    }

    public VehicleDto vehicleToDto(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDto.class);
    }
}
