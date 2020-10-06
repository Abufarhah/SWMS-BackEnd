package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.VehicleDto;

import java.util.List;

public interface VehicleService {

    List<VehicleDto> getVehicles();

    VehicleDto getVehicle(int id);

    VehicleDto addVehicle(VehicleDto vehicleDto);

    VehicleDto updateVehicle(VehicleDto vehicleDto, int id);

    void deleteVehicle(int id);

}
