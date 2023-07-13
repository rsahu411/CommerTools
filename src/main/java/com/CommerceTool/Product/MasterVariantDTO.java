package com.CommerceTool.Product;

import lombok.Data;

import java.util.List;

@Data
public class MasterVariantDTO {
    private String key;
    private String sku;
    private List<PriceDTO> prices;
    private List<ImagesDTO> images;
    private List<AttributesDTO> Attributes;
}
