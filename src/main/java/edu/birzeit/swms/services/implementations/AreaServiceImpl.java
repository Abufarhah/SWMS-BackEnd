package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.exceptions.ResourceAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.AreaMapper;
import edu.birzeit.swms.mappers.BinMapper;
import edu.birzeit.swms.mappers.EmployeeMapper;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.models.Employee;
import edu.birzeit.swms.repositories.AreaRepository;
import edu.birzeit.swms.repositories.BinRepository;
import edu.birzeit.swms.repositories.EmployeeRepository;
import edu.birzeit.swms.services.AreaService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
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
    EmployeeRepository employeeRepository;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    BinMapper binMapper;

    @Autowired
    EmployeeMapper employeeMapper;

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
        List<Bin> binList = (List<Bin>) binRepository.findAll();
        binList.forEach(bin -> {
            if (bin.getArea() == null && area.getPolygon().contains(bin.getLocation().getX(), bin.getLocation().getY())) {
                bin.setArea(area);
                binRepository.save(bin);
            }
        });
        Area savedArea = areaRepository.save(area);
        AreaDto savedAreaDto = areaMapper.areaToDto(savedArea);
        return savedAreaDto;
    }

    @Override
    public AreaDto updateArea(AreaDto areaDto, int id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id));
        area.setName(areaDto.getName());
        Polygon polygon = new Polygon();
        areaDto.getPolygonDto().getPointDtoList().forEach(point ->
                polygon.addPoint((int) (point.getX() * Math.pow(10, 7)), (int) (point.getY() * Math.pow(10, 7))));
        area.setPolygon(polygon);
        Area savedArea = areaRepository.save(area);
        AreaDto savedAreaDto = areaMapper.areaToDto(savedArea);
        return savedAreaDto;
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
            bin.setArea(null);
            binRepository.save(bin);
        } else {
            throw new ResourceNotAssignedException("Area", areaId, "Bin", binId);
        }
    }

    @Override
    public void assignEmployee(int areaId, int employeeId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("Area", "id", areaId));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow((() -> new ResourceNotFoundException("Employee", "id", employeeId)));
        if (area.getEmployeeList().contains(employee)) {
            throw new ResourceAssignedException("Area", areaId, "Employee", employeeId);
        } else {
            area.getEmployeeList().add(employee);
            areaRepository.save(area);
        }
    }

    @Override
    public void unAssignEmployee(int areaId, int employeeId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("Area", "id", areaId));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow((() -> new ResourceNotFoundException("Employee", "id", employeeId)));
        if (area.getEmployeeList().contains(employee)) {
            area.getEmployeeList().remove(employee);
            areaRepository.save(area);
        } else {
            throw new ResourceNotAssignedException("Area", areaId, "Employee", employeeId);
        }
    }

    @Override
    public List<EmployeeDto> getEmployeesOfArea(int id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id));
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        area.getEmployeeList().forEach(employee -> employeeDtoList.add(employeeMapper.employeeToDto(employee)));
        return employeeDtoList;
    }

}
