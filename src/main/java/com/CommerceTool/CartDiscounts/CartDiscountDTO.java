package com.CommerceTool.CartDiscounts;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Data
public class CartDiscountDTO {

    private String name;
    private String description;
    private String Key;
    private String Code;
    private ZonedDateTime ValidFrom;
    private ZonedDateTime ValidUntil;
    private Boolean requiresDiscountCode;
    private String sortOrder;
    private Long permyriad;
    private String currencyCode;
    private Long CentAmount;
    private String cartPredicate;
    private String targetPredicate;
    private CartDiscountValueDto type;
}
