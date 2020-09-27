package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.AreaDto;
import edu.birzeit.swms.services.AreaService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
