package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.models.Bin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BinMapper {

    @Autowired
    ModelMapper modelMapper;

    public Bin dtoToBin(BinDto binDto) {
        return modelMapper.map(binDto, Bin.class);
    }

    public BinDto binToDto(Bin bin) {
        return modelMapper.map(bin, BinDto.class);
    }

}
