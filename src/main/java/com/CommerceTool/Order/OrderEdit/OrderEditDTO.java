package com.CommerceTool.Order.OrderEdit;

import lombok.Data;

import java.util.List;

@Data
public class OrderEditDTO {
    private String key;
    private String orderId;
    private String lineItemId;
    private Long quantity;
    private String comment;

    private Long editVersion;
    private Long resourceVersion;
    private String productId;


    List<StagedActionDetails> stagedActions;

}
