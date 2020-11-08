package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.BinDto;
import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.services.BinService;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/bins")
@Api(tags = "Operations related to bins in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class BinController {

    @Autowired
    BinService binService;

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<BinDto> getBins() {
        return binService.getBins();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public BinDto getBin(@PathVariable int id) {
        return binService.getBin(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public BinDto addBin(@RequestBody BinDto binDto) {
        return binService.addBin(binDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public BinDto updateBin(@RequestBody BinDto binDto, @PathVariable int id) {
        return binService.updateBin(binDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteBin(@PathVariable int id) {
        binService.deleteBin(id);
    }

    @PutMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public BinDto updateBinStatus(@RequestParam Status status, int id) {
        return binService.updateBinStatus(status, id);
    }

}
