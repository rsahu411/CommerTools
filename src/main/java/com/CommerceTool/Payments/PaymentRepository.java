package com.CommerceTool.Payments;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.payment.Payment;
import com.commercetools.api.models.payment.PaymentDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Payment createPayment(PaymentDraft paymentDraft) {

        return apiRoot.payments().post(paymentDraft).executeBlocking().getBody();
    }
}
