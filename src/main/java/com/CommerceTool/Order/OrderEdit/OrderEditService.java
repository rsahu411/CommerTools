package com.CommerceTool.Order.OrderEdit;


import com.CommerceTool.Order.OrderRepository;
import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.order.OrderReference;
import com.commercetools.api.models.order.StagedOrderUpdateAction;
import com.commercetools.api.models.order_edit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderEditService {
    @Autowired
    ProjectApiRoot apiRoot;
    @Autowired
    OrderRepository repository;


    // Create Order-Edit
    public OrderEdit createOrderEdit(OrderEditDTO orderEditDTO) {


        List<StagedOrderUpdateAction> stagedActions = orderEditDTO
                .getStagedActions()
                .stream()
                .map(stagedActionDetails -> {
                    return switch (stagedActionDetails.getAction())
                            {
                                case "changeLineItemQuantity" -> StagedOrderUpdateAction
                                        .changeLineItemQuantityBuilder()
                                        .lineItemId(stagedActionDetails.getLineItemId())
                                        .quantity(stagedActionDetails.getQuantity())
                                        .build();
                                case "addLineItem" -> StagedOrderUpdateAction
                                        .addLineItemBuilder()
                                        .productId(stagedActionDetails.getProductId())
                                        .variantId(stagedActionDetails.getVariantId())
                                        .quantity(stagedActionDetails.getQuantity())
                                        .build();
                                case "removeLineItem" -> StagedOrderUpdateAction
                                        .removeLineItemBuilder()
                                        .lineItemId(stagedActionDetails.getLineItemId())
                                        .build();
                                default -> throw new InvalidActionException(stagedActionDetails.getAction());
                            };
                })
                .collect(Collectors.toList());

        OrderEditDraft orderEditDraft = OrderEditDraft
                .builder()
                .key(orderEditDTO.getKey())
                .resource(OrderReference.builder().id(orderEditDTO.getOrderId()).build())
                .stagedActions(stagedActions)
//                .stagedActions(StagedOrderUpdateAction
//                        .changeLineItemQuantityBuilder()
//                        .lineItemId(orderEditDetails.getLineItemId())
//                        .quantity(orderEditDetails.getQuantity())
//                        .build()
//                )
                .comment(orderEditDTO.getComment())
                .build();

        return repository.createOrderEdit(orderEditDraft);
    }



    // Applied OrderEdit On Order
    public OrderEdit appliedOrderEdit(String orderEditId, OrderEditDTO orderEditDTO) {

        OrderEdit orderEdit = apiRoot.orders().edits().withId(orderEditId).get().executeBlocking().getBody();

        OrderEditApply orderEditApply = OrderEditApply
                .builder()
                .editVersion(orderEditDTO.getEditVersion())
                .resourceVersion(orderEditDTO.getResourceVersion())
                .build();

        OrderEdit appliedOrderEdit = apiRoot
                .orders()
                .edits()
                .withId(orderEdit.getId())
                .apply()
                .post(orderEditApply)
                .executeBlocking()
                .getBody();
        return appliedOrderEdit;

    }





    // Get All Order-Edits
    public OrderEditPagedQueryResponse getAllOrderEdit(String limit)
    {
        OrderEditPagedQueryResponse queryResponse = apiRoot
                .orders()
                .edits()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();

        return queryResponse;
    }




    // Get Order-Edit By Id
    public OrderEdit GetOrderEditById(String orderEditId)
    {
        OrderEdit orderEdit = apiRoot
                .orders()
                .edits()
                .withId(orderEditId)
                .get()
                .executeBlocking()
                .getBody();

        return orderEdit;
    }




    // Delete Order-Edit
    public OrderEdit deleteOrderEditById(String orderEditId,long version)
    {
        OrderEdit orderEdit = apiRoot
                .orders()
                .edits()
                .withId(orderEditId)
                .delete(version)
                .executeBlocking()
                .getBody();

        return orderEdit;
    }


}
