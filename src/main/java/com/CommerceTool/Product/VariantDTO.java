package com.CommerceTool.Product;

import com.commercetools.api.models.common.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantDTO {

    private String key;
    private String sku;
    private List<PriceDTO> prices;
    private List<ImagesDTO> images;
    private List<AttributesDTO> attributes;

}
