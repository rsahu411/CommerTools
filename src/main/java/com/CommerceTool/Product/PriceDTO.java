package com.CommerceTool.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    private String key;
    private String country;
    private String currencyCode;
    private long centAmount;
}
