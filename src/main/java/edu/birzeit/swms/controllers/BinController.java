package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.services.BinService;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/bins")
@Api(tags = "Operations related to bins in SWMS")
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

    @PutMapping
    public BinDto updateBinStatus(@RequestParam Status status, int id) {
        log.info("updateBinStatus");
        return binService.updateBinStatus(status, id);
    }

}
