package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.BinMapper;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.repositories.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BinServiceImpl implements BinService {

    @Autowired
    BinRepository binRepository;

    @Autowired
    BinMapper binMapper;

    @Override
    public List<BinDto> getBins() {
        List<BinDto> binDtoList = new ArrayList<>();
        binRepository.findAll().forEach(bin -> binDtoList.add(binMapper.binToDto(bin)));
        return binDtoList;
    }

    @Override
    public BinDto getBin(int id) {
        Bin bin = binRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bin", "id", id));
        BinDto binDto = binMapper.binToDto(bin);
        return binDto;
    }

    @Override
    public BinDto addBin(BinDto binDto) {
        Bin bin = binMapper.dtoToBin(binDto);
        Bin savedBin = binRepository.save(bin);
        BinDto savedBinDto = binMapper.binToDto(savedBin);
        return savedBinDto;
    }

    @Override
    public BinDto updateBin(BinDto binDto, int id) {
        Bin bin = binRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bin", "id", id));
        bin = binMapper.dtoToBin(binDto);
        Bin savedBin = binRepository.save(bin);
        BinDto savedBinDto = binMapper.binToDto(savedBin);
        return savedBinDto;
    }

    @Override
    public void deleteBin(int id) {
        if (binRepository.existsById(id)) {
            binRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Bin", "id", id);
        }
    }

}
