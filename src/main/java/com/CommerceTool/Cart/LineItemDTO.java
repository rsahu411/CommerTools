package com.CommerceTool.Cart;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemDTO {

    private String sku;
    private Long quantity;


}
