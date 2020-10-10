package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.ReportMapper;
import edu.birzeit.swms.models.Report;
import edu.birzeit.swms.repositories.ReportRepository;
import edu.birzeit.swms.services.ReportService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportMapper reportMapper;

    @Override
    public List<ReportDto> getReports() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportRepository.findAll().forEach(report -> reportDtoList.add(reportMapper.reportToDto(report)));
        return reportDtoList;
    }

    @Override
    public ReportDto getReport(int id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        ReportDto reportDto = reportMapper.reportToDto(report);
        return reportDto;
    }

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        Report report = reportMapper.dtoToReport(reportDto);
        Report savedReport = reportRepository.save(report);
        ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
        return savedReportDto;
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto, int id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        report.setSubject(reportDto.getSubject());
        report.setBody(reportDto.getBody());
        Report savedReport = reportRepository.save(report);
        ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
        return savedReportDto;
    }

    @Override
    public void deleteReport(int id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Report", "id", id);
        }
    }
}
