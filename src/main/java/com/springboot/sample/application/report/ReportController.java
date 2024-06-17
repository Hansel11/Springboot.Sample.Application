package com.springboot.sample.application.report;

import com.springboot.sample.application.report.model.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/date")
    public ResponseEntity<ReportDto> getReportByDate(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestParam(required = false) LocalDate startDate,
                                                     @RequestParam(required = false) LocalDate endDate){

        return new ResponseEntity<>(reportService.getReportByDate(userDetails.getUsername(),startDate,endDate),HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<ReportDto> getReportByProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestParam(required = false) LocalDate startDate,
                                                        @RequestParam(required = false) LocalDate endDate){

        return new ResponseEntity<>(reportService.getReportByProduct(userDetails.getUsername() ,startDate,endDate),HttpStatus.OK);
    }
}
