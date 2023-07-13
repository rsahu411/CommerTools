package com.CommerceTool.Order.OrderEdit;

import lombok.Data;

@Data
public class StagedActionDetails {

    private String action;
    private String email;
    private String lineItemId;
    private String productId;
    private Long variantId;
    private Long quantity;
}
