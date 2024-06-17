package com.springboot.sample.application.transaction.header;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, Long> {
    List<TransactionHeader> findByDataStatusNot(Character dataStatus);
    Optional<TransactionHeader> findByIdAndDataStatusNot(Long id, Character dataStatus);

    @Query(value = "SELECT SUM(p.price*d.quantity) " +
            "FROM transaction_header h " +
            "JOIN transaction_detail d ON h.id = d.header_id " +
            "JOIN product p ON p.id = d.product_id " +
            "WHERE h.id = :headerId " +
            "AND d.data_status <> 'D' " +
            "AND h.data_status <> 'D' " +
            "AND p.data_status <> 'D'"
            , nativeQuery = true)
    Double sumNet(Long headerId);

    @Query(value = "SELECT SUM(p.tax*p.price*d.quantity) " +
            "FROM transaction_header h " +
            "JOIN transaction_detail d ON h.id = d.header_id " +
            "JOIN product p ON p.id = d.product_id " +
            "WHERE h.id = :headerId " +
            "AND d.data_status <> 'D' " +
            "AND h.data_status <> 'D' " +
            "AND p.data_status <> 'D'"
            , nativeQuery = true)
    Double sumTax(Long headerId);

    @Query(value = "SELECT SUM(p.price*(1+p.tax)*d.quantity) " +
            "FROM transaction_header h " +
            "JOIN transaction_detail d ON h.id = d.header_id " +
            "JOIN product p ON p.id = d.product_id " +
            "WHERE h.id = :headerId " +
            "AND d.data_status <> 'D' " +
            "AND h.data_status <> 'D' " +
            "AND p.data_status <> 'D'"
            , nativeQuery = true)
    Double sumTotal(Long headerId);
}
