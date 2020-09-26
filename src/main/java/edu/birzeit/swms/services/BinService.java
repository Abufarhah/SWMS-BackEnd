package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.enums.Status;

import java.util.List;

public interface BinService {

    List<BinDto> getBins();

    BinDto getBin(int id);

    BinDto addBin(BinDto binDto);

    BinDto updateBin(BinDto binDto, int id);

    void deleteBin(int id);

    BinDto updateBinStatus(Status status, int id);


}