package com.springboot.sample.application.report.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DateReport extends ReportDetail {
    private LocalDate time;
    private Double netPaid;
    private Double taxPaid;
    private Double totalPaid;
}
