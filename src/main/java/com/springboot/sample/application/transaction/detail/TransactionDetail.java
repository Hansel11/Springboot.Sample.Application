package com.springboot.sample.application.transaction.detail;

import com.springboot.sample.application.product.Product;
import com.springboot.sample.application.transaction.header.TransactionHeader;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_detail")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private TransactionHeader header;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private Character dataStatus;

    public TransactionDetailDto toDto(){
        return TransactionDetailDto.builder()
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(quantity)
                .totalPrice(quantity * product.getPrice())
                .build();
    }
}
