package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.CitizenDto;

import java.util.List;

public interface CitizenService {

    List<CitizenDto> getCitizens();

    CitizenDto getCitizen(int id);

    CitizenDto addCitizen(CitizenDto citizenDto);

    CitizenDto updateCitizen(CitizenDto citizenDto, int id);

    void deleteCitizen(int id);

}
