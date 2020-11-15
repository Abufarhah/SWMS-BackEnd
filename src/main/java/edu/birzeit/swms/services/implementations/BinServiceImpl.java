package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.PointDto;
import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.BinMapper;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.repositories.AreaRepository;
import edu.birzeit.swms.repositories.BinRepository;
import edu.birzeit.swms.services.BinService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Log
@Service
public class BinServiceImpl implements BinService {

    @Autowired
    BinRepository binRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    BinMapper binMapper;

    @Override
    public List<BinDto> getBins() {
        List<BinDto> binDtoList = new ArrayList<>();
        binRepository.findAll().forEach(bin -> {
            BinDto binDto = binMapper.binToDto(bin);
            binDto.setAreaId(bin.getArea().getId());
            binDtoList.add(binDto);
        });
        return binDtoList;
    }

    @Override
    public BinDto getBin(int id) {
        Bin bin = binRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", id));
        BinDto binDto = binMapper.binToDto(bin);
        binDto.setAreaId(bin.getArea().getId());
        return binDto;
    }

    @Override
    public BinDto addBin(BinDto binDto) {
        Bin bin = binMapper.dtoToBin(binDto);
        Area area = null;
        List<Area> areaList = new ArrayList<>();
        areaRepository.findAll().forEach(a -> areaList.add(a));
        boolean flag=false;
        for (Area a : areaList) {
            if (a.getPolygon().contains(binDto.getLocation().getX()*Math.pow(10,7), binDto.getLocation().getY()*Math.pow(10,7))) {
                area = a;
                flag=true;
                break;
            }
        }
        if(!flag){
            throw new IllegalStateException("Bin doesn't belong to any area");
        }
        bin.setArea(area);
        Bin savedBin = binRepository.save(bin);
        BinDto savedBinDto = binMapper.binToDto(savedBin);
        return savedBinDto;
    }

    @Override
    public BinDto updateBin(BinDto binDto, int id) {
        Bin bin = binRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", id));
        Point point = new Point();
        point.setLocation(binDto.getLocation().getX(), binDto.getLocation().getY());
        bin.setLocation(point);
        if (!bin.getLocation().equals(binDto.getLocation())) {
            Area area = null;
            List<Area> areaList = new ArrayList<>();
            areaRepository.findAll().forEach(a -> areaList.add(a));
            for (Area a : areaList) {
                if (a.getPolygon().contains(bin.getLocation())) {
                    area = a;
                    break;
                }
            }
            bin.setArea(area);
        }
        Bin savedBin = binRepository.save(bin);
        BinDto savedBinDto = binMapper.binToDto(savedBin);
        return savedBinDto;
    }

    @Override
    public void deleteBin(int id) {
        Bin bin = binRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", id)
        );
        bin.getReportList().forEach(report -> report.setBin(null));
        binRepository.deleteById(id);
    }

    @Override
    public BinDto updateBinStatus(Status status, int id) {
        Bin bin = binRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", id));
        bin.setStatus(status);
        Bin savedBin = binRepository.save(bin);
        BinDto savedBinDto = binMapper.binToDto(savedBin);
        return savedBinDto;
    }

    @Override
    public List<BinDto> findByStatus(Status status) {
        List<BinDto> binDtoList = new ArrayList<>();
        binRepository.findByStatus(status).forEach(
                bin -> binDtoList.add(binMapper.binToDto(bin)));
        return binDtoList;
    }

    @Override
    public List<BinDto> findByLocation(PointDto location, int n) {
        PriorityQueue<Bin> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(o -> getDistance(o, location.getX(), location.getY())));
        binRepository.findAll().forEach(bin -> priorityQueue.add(bin));
        List<BinDto> nearestBins = new ArrayList<>();
        int count = n;
        while (count != 0 && priorityQueue.size() != 0) {
            nearestBins.add(binMapper.binToDto(priorityQueue.remove()));
            count--;
        }
        return nearestBins;
    }

    private double getDistance(Bin bin, double latitude, double longitude) {
        if ((bin.getLocation().getX() == latitude) && (bin.getLocation().getY() == longitude)) {
            return 0;
        } else {
            double theta = bin.getLocation().getY() - longitude;
            double dist = Math.sin(Math.toRadians(bin.getLocation().getX()))
                    * Math.sin(Math.toRadians(latitude))
                    + Math.cos(Math.toRadians(bin.getLocation().getX()))
                    * Math.cos(Math.toRadians(latitude))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            return dist;
        }
    }
}
