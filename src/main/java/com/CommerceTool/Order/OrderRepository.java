package com.CommerceTool.Order;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.commercetools.api.models.order_edit.OrderEdit;
import com.commercetools.api.models.order_edit.OrderEditDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Order createOrder(OrderFromCartDraft order) {
        return apiRoot.orders().post(order).executeBlocking().getBody();
    }

    public OrderEdit createOrderEdit(OrderEditDraft orderEditDraft) {
        return apiRoot.orders().edits().post(orderEditDraft).executeBlocking().getBody();
    }
}
