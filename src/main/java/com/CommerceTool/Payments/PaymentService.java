package com.CommerceTool.Payments;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.customer.CustomerResourceIdentifier;
import com.commercetools.api.models.payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    ProjectApiRoot projectApiRoot;
    @Autowired
    PaymentRepository repository;


    // Create Payments
    public Payment createPayment(PaymentDTO paymentDTO) {


       List<TransactionDraft> transactionDraftList = paymentDTO.getTransactions()
                .stream()
                .map( transactionDetails -> {
                    return TransactionDraft
                            .builder()
                            .timestamp(transactionDetails.getTimestamp())
                            .type(transactionDetails.getType())
                            .amount(Money
                                    .builder()
                                    .currencyCode(transactionDetails.getCurrencyCode())
                                    .centAmount(transactionDetails.getCentAmount())
                                    .build())
                            .state(transactionDetails.getState())
                            .build();
                        }
                )
                .collect(Collectors.toList());


        PaymentDraft paymentDraft = PaymentDraft
                .builder()
                .key(paymentDTO.getKey())
                .customer(CustomerResourceIdentifier.builder().id(paymentDTO.getCustomerId()).build())
                .interfaceId(paymentDTO.getInterfaceId())
                .amountPlanned(Money
                        .builder()
                        .currencyCode(paymentDTO.getCurrencyCode())
                        .centAmount(paymentDTO.getCentAmount())
                        .build())
                .paymentMethodInfo(PaymentMethodInfo
                        .builder()
                        .paymentInterface(paymentDTO.getPaymentInterface())
                        .method(paymentDTO.getMethod())
                        .name(LocalizedString.ofEnglish(paymentDTO.getName()))
                        .build())
                .transactions(transactionDraftList)
                .build();

        return repository.createPayment(paymentDraft);
    }


    // Get All Payments
    public PaymentPagedQueryResponse getAllPayments(String limit)
    {
        PaymentPagedQueryResponse queryResponse = projectApiRoot
                .payments()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();

        return queryResponse;
    }


    // Get Payment By Id
    public Payment getPaymentsById(String id) {

        Payment payment = projectApiRoot
                .payments()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();

        return payment;

    }


    // Delete Payment By Id
    public Payment deletePaymentById(String id) {

        Payment payment = projectApiRoot.payments().withId(id).get().executeBlocking().getBody();

        return projectApiRoot
                .payments()
                .withId(id)
                .delete(payment.getVersion())
                .executeBlocking()
                .getBody();
    }
}
