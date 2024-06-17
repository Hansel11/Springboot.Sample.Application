package com.springboot.sample.application.transaction.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetailDto {
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;
}
