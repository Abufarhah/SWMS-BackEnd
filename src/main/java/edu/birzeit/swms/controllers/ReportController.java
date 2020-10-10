package edu.birzeit.swms.controllers;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.services.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/reports")
@Api(tags = "Operations related to reports in SWMS")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully done operation"),
        @ApiResponse(code = 201, message = "Successfully created entity"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping
    public List<ReportDto> getReports() {
        return reportService.getReports();
    }

    @GetMapping("/{id}")
    public ReportDto getReport(@PathVariable int id) {
        return reportService.getReport(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto addReport(@RequestBody ReportDto reportDto) {
        return reportService.addReport(reportDto);
    }

    @PutMapping("/{id}")
    public ReportDto updateReport(@RequestBody ReportDto reportDto, @PathVariable int id) {
        return reportService.updateReport(reportDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
    }

}
