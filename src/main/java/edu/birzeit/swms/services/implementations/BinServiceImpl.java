package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.NotificationRequestDto;
import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.exceptions.CustomException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.BinMapper;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.repositories.AreaRepository;
import edu.birzeit.swms.repositories.BinRepository;
import edu.birzeit.swms.services.BinService;
import edu.birzeit.swms.services.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static edu.birzeit.swms.configurations.Constants.*;

@Log
@Service
public class BinServiceImpl implements BinService {

    @Autowired
    BinRepository binRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    BinMapper binMapper;

    @Override
    public List<BinDto> getBins() {
        List<BinDto> binDtoList = new ArrayList<>();
        binRepository.findAll().forEach(bin -> {
            BinDto binDto = binMapper.binToDto(bin);
            if (bin.getArea() != null) {
                binDto.setAreaId(bin.getArea().getId());
            } else {
                binDto.setAreaId(0);
            }
            binDtoList.add(binDto);
        });
        return binDtoList;
    }

    @Override
    public BinDto getBin(int id) {
        Bin bin = binRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", id));
        BinDto binDto = binMapper.binToDto(bin);
        if (bin.getArea() != null) {
            binDto.setAreaId(bin.getArea().getId());
        } else {
            binDto.setAreaId(0);
        }
        return binDto;
    }

    @Override
    public BinDto addBin(BinDto binDto) {
        Bin bin = binMapper.dtoToBin(binDto);
        Area area = null;
        List<Area> areaList = new ArrayList<>();
        areaRepository.findAll().forEach(a -> areaList.add(a));
        boolean flag = false;
        for (Area a : areaList) {
            if (a.getPolygon().contains(binDto.getLocation().getX() * Math.pow(10, 7), binDto.getLocation().getY() * Math.pow(10, 7))) {
                area = a;
                flag = true;
                break;
            }
        }
        if (flag) {
            bin.setArea(area);
        } else {
            bin.setArea(null);
        }
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

    public List<BinDto> findByLocationAndStatus(Double latitude, Double longitude, Integer n, Status status) {
        List<BinDto> binDtoList = new ArrayList<>();
        if (status == null && latitude == null & longitude == null && n == null) {
            binRepository.findAll().forEach(
                    bin -> binDtoList.add(binMapper.binToDto(bin)));
        } else if (status != null && latitude == null & longitude == null && n == null) {
            binRepository.findByStatus(status).forEach(
                    bin -> binDtoList.add(binMapper.binToDto(bin)));
            return binDtoList;
        } else if (status == null) {
            PriorityQueue<Bin> priorityQueue = new PriorityQueue<>(
                    Comparator.comparingDouble(o -> getDistance(o, latitude, longitude)));
            binRepository.findAll().forEach(bin -> priorityQueue.add(bin));
            int count = n;
            while (count != 0 && priorityQueue.size() != 0) {
                binDtoList.add(binMapper.binToDto(priorityQueue.remove()));
                count--;
            }
        } else {
            PriorityQueue<Bin> priorityQueue = new PriorityQueue<>(
                    Comparator.comparingDouble(o -> getDistance(o, latitude, longitude)));
            binRepository.findAll().forEach(bin -> priorityQueue.add(bin));
            int count = n;
            while (count != 0 && priorityQueue.size() != 0) {
                binDtoList.add(binMapper.binToDto(priorityQueue.remove()));
                count--;
            }
        }
        return binDtoList;
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

    public String sendEmergencyNotification(int binId) {
        Bin bin = binRepository.findById(binId).orElseThrow(
                () -> new ResourceNotFoundException("Bin", "id", binId)
        );
        if (bin.getArea() != null) {
            int areaId = bin.getArea().getId();
            String topicName = TOPIC_PREFIX +TOPIC_DELIMITER+areaId;
            String title=EMERGENCY_NOTIFICATION_TITLE;
            String message=String.format(EMERGENCY_NOTIFICATION_MESSAGE,binId,areaId);
            NotificationRequestDto notificationRequestDto=new NotificationRequestDto();
            notificationRequestDto.setTarget(topicName);
            notificationRequestDto.setTitle(title);
            notificationRequestDto.setBody(message);
            return notificationService.sendPnsToTopic(notificationRequestDto);
        } else {
            throw new CustomException("Bin: " + binId + "doesn't belong to any area",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
