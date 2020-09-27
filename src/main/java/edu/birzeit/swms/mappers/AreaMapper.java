package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.models.Area;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {

    @Autowired
    ModelMapper modelMapper;

    public Area dtoToArea(AreaDto areaDto) {
        return modelMapper.map(areaDto, Area.class);
    }

    public AreaDto areaToDto(Area area) {
        return modelMapper.map(area, AreaDto.class);
    }

}
