package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.services.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bins")
public class BinController {

    @Autowired
    BinService binService;

    @GetMapping
    public List<BinDto> getBins() {
        return binService.getBins();
    }

    @GetMapping("/{id}")
    public BinDto getBin(@PathVariable int id) {
        return binService.getBin(id);
    }

    @PostMapping
    public BinDto addBin(@RequestBody BinDto binDto) {
        return binService.addBin(binDto);
    }

    @PutMapping("/{id}")
    public BinDto updateBin(@RequestBody BinDto binDto, @PathVariable int id) {
        return binService.updateBin(binDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBin(@PathVariable int id) {
        binService.deleteBin(id);
    }
}
