package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.ReportDto;
import edu.birzeit.swms.enums.ReportStatus;
import edu.birzeit.swms.exceptions.CustomException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.ReportMapper;
import edu.birzeit.swms.models.Report;
import edu.birzeit.swms.models.User;
import edu.birzeit.swms.repositories.BinRepository;
import edu.birzeit.swms.repositories.ReportRepository;
import edu.birzeit.swms.repositories.UserRepository;
import edu.birzeit.swms.services.ReportService;
import edu.birzeit.swms.utils.SWMSUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static edu.birzeit.swms.configurations.Constants.ADMIN_USERNAME;

@Log
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    BinRepository binRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SWMSUtil util;

    @Autowired
    User admin;

    @Override
    public List<ReportDto> getReports() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportRepository.findAll().forEach(report -> {
            ReportDto reportDto = reportMapper.reportToDto(report);
            reportDto.setBinId(report.getBin().getId());
            reportDto.setUserId(report.getFrom().getId());
            reportDtoList.add(reportDto);
        });
        User user = util.getLoggedInUser();
        if (!user.getUsername().equals(ADMIN_USERNAME)) {
            return reportDtoList.stream().filter(reportDto ->
                    reportDto.getUserId() == user.getId()).collect(Collectors.toList());
        }
        return reportDtoList;
    }

    @Override
    public ReportDto getReport(int id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report", "id", id));
        ReportDto reportDto = reportMapper.reportToDto(report);
        reportDto.setBinId(report.getBin().getId());
        reportDto.setUserId(report.getFrom().getId());
        return reportDto;
    }

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        Report report = reportMapper.dtoToReport(reportDto);
        if (reportDto.getBinId() != 0) {
            report.setBin(binRepository.findById(reportDto.getBinId()).orElseThrow(
                    () -> new ResourceNotFoundException("Bin", "id", reportDto.getBinId())));
        }
        User user = util.getLoggedInUser();
        report.setFrom(user);
        report.setStatus(ReportStatus.SENT);
        Report savedReport = reportRepository.save(report);
        ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
        savedReportDto.setBinId(savedReport.getBin().getId());
        savedReportDto.setUserId(savedReport.getFrom().getId());
        return savedReportDto;
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto, int id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report", "id", id));
        User user = util.getLoggedInUser();
        if (user.getUsername().equals(report.getFrom().getUsername())) {
            report.setSubject(reportDto.getSubject());
            report.setBody(reportDto.getBody());
            if (reportDto.getBinId() != 0) {
                report.setBin(binRepository.findById(reportDto.getBinId()).orElseThrow(
                        () -> new ResourceNotFoundException("Bin", "id", reportDto.getBinId())));
            }
            Report savedReport = reportRepository.save(report);
            ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
            savedReportDto.setBinId(savedReport.getBin().getId());
            savedReportDto.setUserId(savedReport.getFrom().getId());
            return savedReportDto;
        } else {
            throw new CustomException("you are not authorized to update this report", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void deleteReport(int id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report", "id", id));
        User user = util.getLoggedInUser();
        if (user.getUsername().equals(report.getFrom().getUsername()) || util.isAdmin()) {
            reportRepository.deleteById(id);
        } else {
            throw new CustomException("you are not authorized to update this report", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ReportDto updateReportStatus(ReportStatus status, int id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report", "id", id));
        report.setStatus(status);
        Report savedReport = reportRepository.save(report);
        ReportDto savedReportDto = reportMapper.reportToDto(savedReport);
        savedReportDto.setBinId(savedReport.getBin().getId());
        savedReportDto.setUserId(savedReport.getFrom().getId());
        return savedReportDto;
    }

}
