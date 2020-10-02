package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.exceptions.ResourceAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.AreaMapper;
import edu.birzeit.swms.mappers.BinMapper;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.repositories.AreaRepository;
import edu.birzeit.swms.repositories.BinRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    BinRepository binRepository;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    BinMapper binMapper;

    @Override
    public List<AreaDto> getAreas() {
        List<AreaDto> areaDtoList = new ArrayList<>();
        areaRepository.findAll().forEach(bin -> areaDtoList.add(areaMapper.areaToDto(bin)));
        return areaDtoList;
    }

    @Override
    public AreaDto getArea(int id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id));
        AreaDto areaDto = areaMapper.areaToDto(area);
        return areaDto;
    }

    @Override
    public AreaDto addArea(AreaDto areaDto) {
        Area area = areaMapper.dtoToArea(areaDto);
        Area savedArea = areaRepository.save(area);
        AreaDto savAreaDto = areaMapper.areaToDto(savedArea);
        return savAreaDto;
    }

    @Override
    public AreaDto updateArea(AreaDto areaDto, int id) {
        if (areaRepository.existsById(id)) {
            Area area = areaMapper.dtoToArea(areaDto);
            Area savedArea = areaRepository.save(area);
            AreaDto savedAreaDto = areaMapper.areaToDto(savedArea);
            return savedAreaDto;
        } else {
            throw new ResourceNotFoundException("Area", "id", id);
        }
    }

    @Override
    public void deleteArea(int id) {
        if (areaRepository.existsById(id)) {
            areaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Area", "id", id);
        }
    }

    @Override
    public List<BinDto> getBinsOfArea(int id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id));
        List<BinDto> binDtoList = new ArrayList<>();
        area.getBinList().forEach(bin -> binDtoList.add(binMapper.binToDto(bin)));
        return binDtoList;
    }

    @Override
    public void assignBin(int areaId, int binId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("Area", "id", areaId));
        Bin bin = binRepository.findById(binId).orElseThrow((() -> new ResourceNotFoundException("Bin", "id", binId)));
        if (area.getBinList().contains(bin)) {
            throw new ResourceAssignedException("Area", areaId, "Bin", binId);
        } else {
            bin.setArea(area);
            binRepository.save(bin);
        }
    }

    @Override
    public void unAssignBin(int areaId, int binId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("Area", "id", areaId));
        Bin bin = binRepository.findById(binId).orElseThrow((() -> new ResourceNotFoundException("Bin", "id", binId)));
        if (area.getBinList().contains(bin)) {
            area.getBinList().remove(bin);
            areaRepository.save(area);
        } else {
            throw new ResourceNotAssignedException("Area", areaId, "Bin", binId);
        }
    }

}
