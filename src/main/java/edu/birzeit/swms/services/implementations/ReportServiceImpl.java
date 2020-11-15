package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.ReportMapper;
import edu.birzeit.swms.models.Report;
import edu.birzeit.swms.repositories.BinRepository;
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

    @Autowired
    BinRepository binRepository;

    @Override
    public List<ReportDto> getReports() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportRepository.findAll().forEach(report -> {
            ReportDto reportDto = reportMapper.reportToDto(report);
            reportDto.setBinId(report.getBin().getId());
            reportDtoList.add(reportDto);
        });
        return reportDtoList;
    }

    @Override
    public ReportDto getReport(int id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report", "id", id));
        ReportDto reportDto = reportMapper.reportToDto(report);
        reportDto.setBinId(report.getBin().getId());
        return reportDto;
    }

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        Report report = reportMapper.dtoToReport(reportDto);
        if (reportDto.getBinId() != 0) {
            report.setBin(binRepository.findById(reportDto.getBinId()).orElseThrow(
                    () -> new ResourceNotFoundException("Bin", "id", reportDto.getBinId())));
        }
        Report savedReport = reportRepository.save(report);
        ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
        return savedReportDto;
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto, int id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        report.setSubject(reportDto.getSubject());
        report.setBody(reportDto.getBody());
        if (reportDto.getBinId() != 0) {
            report.setBin(binRepository.findById(reportDto.getBinId()).orElseThrow(
                    () -> new ResourceNotFoundException("Bin", "id", reportDto.getBinId())));
        }
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
