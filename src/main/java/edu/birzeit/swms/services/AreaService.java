package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.dtos.EmployeeDto;
import edu.birzeit.swms.exceptions.ResourceAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Employee;

import java.util.List;

public interface AreaService {

    List<AreaDto> getAreas();

    AreaDto getArea(int id);

    AreaDto addArea(AreaDto areaDto);

    AreaDto updateArea(AreaDto areaDto, int id);

    void deleteArea(int id);

    List<BinDto> getBinsOfArea(int id);

    void assignBin(int areaId, int binId);

    void unAssignBin(int areaId, int binId);

    void assignEmployee(int areaId, int employeeId);

    void unAssignEmployee(int areaId, int employeeId);

    List<EmployeeDto> getEmployeesOfArea(int id);

}
