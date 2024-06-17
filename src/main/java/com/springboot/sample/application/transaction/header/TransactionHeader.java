package com.springboot.sample.application.transaction.header;

import com.springboot.sample.application.transaction.PaymentMethod;
import com.springboot.sample.application.transaction.PaymentStatus;
import com.springboot.sample.application.transaction.detail.TransactionDetail;
import com.springboot.sample.application.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_header")
public class TransactionHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate transactionTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private Character dataStatus;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> details;

    public String getUsername(){
        return customer.getUsername();
    }
}
