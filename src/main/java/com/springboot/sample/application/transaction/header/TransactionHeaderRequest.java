package com.springboot.sample.application.transaction.header;

import com.springboot.sample.application.transaction.PaymentMethod;
import com.springboot.sample.application.transaction.PaymentStatus;
import com.springboot.sample.application.transaction.detail.TransactionDetailRequest;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionHeaderRequest {
    private Long id;
    private Long customerId;
    private LocalDate transactionTime;

    private List<TransactionDetailRequest> details;

    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
