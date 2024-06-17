package com.springboot.sample.application.report;

import com.springboot.sample.application.report.model.DateReport;
import com.springboot.sample.application.report.model.ProductReport;
import com.springboot.sample.application.report.model.ReportDto;
import com.springboot.sample.application.transaction.header.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface ReportRepository extends JpaRepository<TransactionHeader, Long> {
    @Query(value = "SELECT new com.springboot.sample.application.report.model.ReportDto ( " +
            "SUM(p.price*d.quantity) AS grandNetPaid, " +
            "SUM(p.price*p.tax*d.quantity) AS grandTaxPaid, " +
            "SUM(p.price*(1+p.tax)*d.quantity) AS grandTotalPaid ) " +
            "FROM TransactionHeader h " +
            "JOIN h.details d " +
            "JOIN d.product p " +
            "WHERE h.customer.username = ?1 " +
            "AND (?2 IS NULL OR h.transactionTime >= ?2) " +
            "AND (?3 IS NULL OR h.transactionTime <= ?3) " +
            "AND h.paymentStatus = 'PAID' " +
            "AND d.dataStatus <> 'D' " +
            "AND h.dataStatus <> 'D' " +
            "AND p.dataStatus <> 'D'")
    ReportDto getUserReportByDate(String username, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT new com.springboot.sample.application.report.model.DateReport ( " +
            "h.transactionTime AS time, " +
            "SUM(p.price*d.quantity) AS netPaid, " +
            "SUM(p.price*p.tax*d.quantity) AS taxPaid, " +
            "SUM(p.price*(1+p.tax)*d.quantity) AS totalPaid ) " +
            "FROM TransactionHeader h " +
            "JOIN h.details d " +
            "JOIN d.product p " +
            "WHERE h.customer.username = ?1 " +
            "AND (?2 IS NULL OR h.transactionTime >= ?2) " +
            "AND (?3 IS NULL OR h.transactionTime <= ?3) " +
            "AND h.paymentStatus = 'PAID' " +
            "AND d.dataStatus <> 'D' " +
            "AND h.dataStatus <> 'D' " +
            "AND p.dataStatus <> 'D' " +
            "GROUP BY h.transactionTime")
    List<DateReport> getUserReportDetailByDate(String username, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT new com.springboot.sample.application.report.model.ProductReport ( " +
            "p.name AS productName, " +
            "SUM(p.price*d.quantity) AS netPaid, " +
            "SUM(p.price*p.tax*d.quantity) AS taxPaid, " +
            "SUM(p.price*(1+p.tax)*d.quantity) AS totalPaid ) " +
            "FROM TransactionHeader h " +
            "JOIN h.details d " +
            "JOIN d.product p " +
            "WHERE h.customer.username = ?1 " +
            "AND (?2 IS NULL OR h.transactionTime >= ?2) " +
            "AND (?3 IS NULL OR h.transactionTime <= ?3) " +
            "AND h.paymentStatus = 'PAID' " +
            "AND d.dataStatus <> 'D' " +
            "AND h.dataStatus <> 'D' " +
            "AND p.dataStatus <> 'D' " +
            "GROUP BY p")
    List<ProductReport> getUserReportDetailByProduct(String username, LocalDate startDate, LocalDate endDate);

}
