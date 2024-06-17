package com.springboot.sample.application.report;

import com.springboot.sample.application.report.model.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportDto getReportByDate(String customerName,
                                     LocalDate startDate,
                                     LocalDate endDate){

        ReportDto report = reportRepository.getUserReportByDate(customerName, startDate, endDate);
        report.setUsername(customerName);
        report.setReports(reportRepository.getUserReportDetailByDate(customerName, startDate, endDate));
        return report;
    }

    public ReportDto getReportByProduct(String customerName,
                                        LocalDate startDate,
                                        LocalDate endDate){

        ReportDto report = reportRepository.getUserReportByDate(customerName, startDate, endDate);
        report.setUsername(customerName);
        report.setReports(reportRepository.getUserReportDetailByProduct(customerName, startDate, endDate));
        return report;
    }
}
