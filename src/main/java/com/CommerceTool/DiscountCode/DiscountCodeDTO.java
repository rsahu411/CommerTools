package com.CommerceTool.DiscountCode;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeDTO {
    private String name;
    private String description;
    private String code;
    private ZonedDateTime validFrom;

    private ZonedDateTime validUntil;
    private Boolean isActive;
    private long maxApplication;
    private long maxApplicationPerCustomer;
    private List<CartDiscountVariable> cartDiscountVariables;

}

