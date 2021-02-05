package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.RoundDto;
import edu.birzeit.swms.models.Round;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoundMapper {

    @Autowired
    ModelMapper modelMapper;

    public Round dtoToRound(RoundDto roundDto) {
        return modelMapper.map(roundDto, Round.class);
    }

    public RoundDto roundToDto(Round round) {
        RoundDto roundDto = modelMapper.map(round, RoundDto.class);
        roundDto.setBinIdsList(round.getBinList().stream().map(bin ->
                bin.getId()).collect(Collectors.toList()));
        roundDto.setUserId(round.getEmployee().getId());
        return roundDto;
    }

}
