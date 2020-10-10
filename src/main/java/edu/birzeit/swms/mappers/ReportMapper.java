package edu.birzeit.swms.mappers;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.models.Report;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    @Autowired
    ModelMapper modelMapper;

    public Report dtoToReport(ReportDto reportDto) {
        return modelMapper.map(reportDto, Report.class);
    }

    public ReportDto reportToDto(Report report) {
        return modelMapper.map(report, ReportDto.class);
    }

}
