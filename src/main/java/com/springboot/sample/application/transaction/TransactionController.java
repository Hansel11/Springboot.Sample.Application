package com.springboot.sample.application.transaction;

import com.springboot.sample.application.transaction.header.TransactionHeaderDto;
import com.springboot.sample.application.transaction.header.TransactionHeaderRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionHeaderDto>> getTransaction(@RequestParam(required = false) String customerName,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                     @RequestParam(required = false) PaymentMethod paymentMethod,
                                                                     @RequestParam(required = false) PaymentStatus paymentStatus,
                                                                     @RequestParam(required = false) String createdBy,
                                                                     @RequestParam(required = false) String sortType){

        return new ResponseEntity<>(transactionService.getTransactions(
                customerName,
                startDate,
                endDate,
                paymentMethod,
                paymentStatus,
                createdBy,
                sortType),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionHeaderDto> addTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody TransactionHeaderRequest request){
        return ResponseEntity.ok(transactionService.addTransaction(request, userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<TransactionHeaderDto> editTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestParam Long transactionId,
                                                            @RequestBody TransactionHeaderRequest request){
        return ResponseEntity.ok(transactionService.editTransaction(transactionId, request, userDetails.getUsername()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @RequestParam Long transactionId) {
        return ResponseEntity.ok(transactionService.deleteTransaction(transactionId, userDetails.getUsername()));
    }

}
