package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.CitizenDto;
import edu.birzeit.swms.models.Citizen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitizenMapper {

    @Autowired
    ModelMapper modelMapper;

    public Citizen dtoToCitizen(CitizenDto citizenDto) {
        return modelMapper.map(citizenDto, Citizen.class);
    }

    public CitizenDto citizenToDto(Citizen citizen) {
        return modelMapper.map(citizen, CitizenDto.class);
    }

}
