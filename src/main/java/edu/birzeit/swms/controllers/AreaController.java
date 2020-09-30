package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.exceptions.ResourceAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotAssignedException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.services.AreaService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log
@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    AreaService areaService;

    @GetMapping
    public List<AreaDto> getAreas() {
        return areaService.getAreas();
    }

    @GetMapping("/{id}")
    public AreaDto getArea(@PathVariable int id) {
        return areaService.getArea(id);
    }

    @PostMapping
    public AreaDto addArea(@RequestBody AreaDto areaDto) {
        return areaService.addArea(areaDto);
    }

    @PutMapping("/{id}")
    public AreaDto updateArea(@RequestBody AreaDto areaDto, @PathVariable int id) {
        return areaService.updateArea(areaDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteArea(@PathVariable int id) {
        areaService.deleteArea(id);
    }

    @GetMapping("/{id}/bins")
    public List<BinDto> getBinsOfArea(@PathVariable int id) {
        return areaService.getBinsOfArea(id);
    }

    @GetMapping("{areaId}/assignBin/{binId}")
    public void assignBin(@PathVariable int areaId, @PathVariable int binId) {
        areaService.assignBin(areaId, binId);

    }

    @GetMapping("{areaId}/unassignBin/{binId}")
    public void unAssignBin(@PathVariable int areaId, @PathVariable int binId) {
        areaService.unAssignBin(areaId, binId);
    }

}
