package com.springboot.sample.application.report.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportDto {
    private String username;
    private Double grandNetPaid;
    private Double grandTaxPaid;
    private Double grandTotalPaid;

    private List<? extends ReportDetail> reports;
}
