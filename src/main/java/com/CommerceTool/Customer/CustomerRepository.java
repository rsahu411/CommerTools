package com.CommerceTool.Customer;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Customer createCustomer(CustomerDraft CustomerDraft) {
        return apiRoot
                .customers()
                .post(CustomerDraft)
                .executeBlocking()
                .getBody()
                .getCustomer();
    }

    public CustomerSignInResult getCustomerByLogin(CustomerSignin customerSignin) {
        return apiRoot.login().post(customerSignin).executeBlocking().getBody();
    }

    public Customer resetPassword(CustomerResetPassword customerResetPassword) {
        return apiRoot.customers().passwordReset().post(customerResetPassword).executeBlocking().getBody();
    }

    public Customer changePassword(CustomerChangePassword changePassword) {
        return apiRoot.customers().password().post(changePassword).executeBlocking().getBody();
    }

    public CustomerToken getCustomerToken(CustomerCreatePasswordResetToken customerCreatePasswordResetToken) {
        return apiRoot.customers().passwordToken().post(customerCreatePasswordResetToken).executeBlocking().getBody();
    }

    public CustomerGroup createCustomerGroup(CustomerGroupDraft customerGroupDraft) {
        return apiRoot.customerGroups().post(customerGroupDraft).executeBlocking().getBody();
    }



}
