package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.enums.ReportStatus;

import java.util.List;

public interface ReportService {

    List<ReportDto> getReports();

    ReportDto getReport(int id);

    ReportDto addReport(ReportDto reportDto);

    ReportDto updateReport(ReportDto reportDto, int id);

    void deleteReport(int id);

    ReportDto updateReportStatus(ReportStatus status, int id);

}
