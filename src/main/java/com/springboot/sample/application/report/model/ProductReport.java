package com.springboot.sample.application.report.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductReport extends ReportDetail{
    private String productName;
    private Double netPaid;
    private Double taxPaid;
    private Double totalPaid;
}
