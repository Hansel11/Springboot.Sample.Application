package com.springboot.sample.application.transaction;

import com.springboot.sample.application.product.ProductService;
import com.springboot.sample.application.transaction.detail.TransactionDetail;
import com.springboot.sample.application.transaction.detail.TransactionDetailRepository;
import com.springboot.sample.application.transaction.header.TransactionHeader;
import com.springboot.sample.application.transaction.header.TransactionHeaderDto;
import com.springboot.sample.application.transaction.header.TransactionHeaderRepository;
import com.springboot.sample.application.transaction.header.TransactionHeaderRequest;
import com.springboot.sample.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionHeaderRepository tHeaderRepository;
    private final TransactionDetailRepository tDetailRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();

    public TransactionHeader findTransactionById(Long id){
        return tHeaderRepository.findByIdAndDataStatusNot(id,'D').orElseThrow(
                () -> new RuntimeException("Transaction not found!")
        );
    }

    public List<TransactionHeaderDto> getTransactions(String customerName,
                                                      LocalDate startDate,
                                                      LocalDate endDate,
                                                      PaymentMethod paymentMethod,
                                                      PaymentStatus paymentStatus,
                                                      String createdBy,
                                                      String sortType){

        return tHeaderRepository.findByDataStatusNot('D')
                .stream()
                .filter(header -> (customerName == null || header.getUsername().equals(customerName))
                                && (startDate == null || header.getTransactionTime().isAfter(startDate))
                                && (endDate == null || header.getTransactionTime().isBefore(endDate.plusDays(1)))
                                && (paymentMethod == null || header.getPaymentMethod().equals(paymentMethod))
                                && (paymentStatus == null || header.getPaymentStatus().equals(paymentStatus))
                                && (createdBy == null || header.getCreatedBy().equals(createdBy)))
                .sorted(Comparator.comparing(TransactionHeader::getTransactionTime,
                (sortType != null && sortType.equals("oldest")) ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .map(this::mapTransaction)
                .toList();
    }

    public TransactionHeaderDto addTransaction(TransactionHeaderRequest request, String createdBy){
        TransactionHeader header = tHeaderRepository.save(
                TransactionHeader.builder()
                .customer(userService.findUserById(request.getCustomerId()))
                .transactionTime(request.getTransactionTime())
                .paymentStatus(request.getPaymentStatus())
                .paymentMethod(request.getPaymentMethod())
                .createdAt(LocalDate.now())
                .createdBy(createdBy)
                .dataStatus('A')
                .build());

        List<TransactionDetail> details = request.getDetails().stream()
                .map(detail ->
                    TransactionDetail.builder()
                        .header(header)
                        .product(productService.findProductById(detail.getProductId()))
                        .quantity(detail.getQuantity())
                        .createdAt(LocalDate.now())
                        .createdBy(createdBy)
                        .dataStatus('A')
                        .build()
                ).toList();
        tDetailRepository.saveAll(details);

        header.setDetails(details);
        return mapTransaction(header);
    }

    public TransactionHeaderDto editTransaction(Long tranId,
                                                TransactionHeaderRequest request,
                                                String updatedBy){
        TransactionHeader header = findTransactionById(tranId);
        header.setCustomer(userService.findUserById(request.getCustomerId()));
        header.setTransactionTime(request.getTransactionTime());
        header.setPaymentMethod(request.getPaymentMethod());
        header.setPaymentStatus(request.getPaymentStatus());

        // Delete all current details
        List<TransactionDetail> currDetails = header.getDetails();
        for(TransactionDetail detail : currDetails){
            detail.setUpdatedBy(updatedBy);
            detail.setUpdatedAt(LocalDate.now());
            detail.setDataStatus('D');
        }
        tDetailRepository.saveAll(currDetails);

        // Insert all new details
        List<TransactionDetail> newDetails = request.getDetails().stream()
                .map(detail ->
                        TransactionDetail.builder()
                                .header(header)
                                .product(productService.findProductById(detail.getProductId()))
                                .quantity(detail.getQuantity())
                                .createdAt(LocalDate.now())
                                .createdBy(updatedBy)
                                .dataStatus('A')
                                .build()
                ).toList();
        tDetailRepository.saveAll(newDetails);

        header.setUpdatedBy(updatedBy);
        header.setUpdatedAt(LocalDate.now());
        tHeaderRepository.save(header);

        header.setDetails(newDetails);
        return mapTransaction(header);
    }

    public String deleteTransaction(Long tranId, String deletedBy){
        TransactionHeader header = findTransactionById(tranId);
        List<TransactionDetail> currDetails = header.getDetails();
        for(TransactionDetail detail : currDetails){
            detail.setUpdatedBy(deletedBy);
            detail.setUpdatedAt(LocalDate.now());
            detail.setDataStatus('D');
        }
        tDetailRepository.saveAll(currDetails);

        header.setDataStatus('D');
        header.setUpdatedBy(deletedBy);
        header.setUpdatedAt(LocalDate.now());
        tHeaderRepository.save(header);
        return "Transaction successfully deleted!";
    }

    private TransactionHeaderDto mapTransaction(TransactionHeader header){
        TransactionHeaderDto dto = mapper.map(header, TransactionHeaderDto.class);
        dto.setDetails(header.getDetails().stream()
                .filter(detail -> detail.getDataStatus() != 'D')
                .map(TransactionDetail::toDto).toList());
        dto.setNetPaid(tHeaderRepository.sumNet(header.getId()));
        dto.setTaxPaid(tHeaderRepository.sumTax(header.getId()));
        dto.setTotalPaid(tHeaderRepository.sumTotal(header.getId()));

        return dto;
    }
}
