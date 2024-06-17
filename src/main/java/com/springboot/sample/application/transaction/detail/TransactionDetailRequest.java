package com.springboot.sample.application.transaction.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetailRequest {
    private Long productId;
    private Integer quantity;
}
