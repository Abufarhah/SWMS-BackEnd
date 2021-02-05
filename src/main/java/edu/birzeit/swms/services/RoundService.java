package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.RoundDto;
import edu.birzeit.swms.enums.RoundStatus;

import java.util.List;

public interface RoundService {

    List<RoundDto> getRounds();

    RoundDto getRound(int id);

    RoundDto addRound(RoundDto roundDto);

    RoundDto updateRound(RoundDto roundDto, int id);

    void deleteRound(int id);

    RoundDto updateRoundStatus(RoundStatus status, int id);

}
