package com.springboot.sample.application.transaction.header;

import com.springboot.sample.application.transaction.PaymentMethod;
import com.springboot.sample.application.transaction.PaymentStatus;
import com.springboot.sample.application.transaction.detail.TransactionDetailDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionHeaderDto {
    private Long id;
    private String username;
    private LocalDate transactionTime;

    private List<TransactionDetailDto> details;

    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private Double netPaid;
    private Double taxPaid;
    private Double totalPaid;

    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private Character dataStatus;
}
