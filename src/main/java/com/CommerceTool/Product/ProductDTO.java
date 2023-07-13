package com.CommerceTool.Product;

import com.commercetools.api.models.common.UpdateAction;
import com.commercetools.api.models.product_type.AttributeConstraintEnum;
import com.commercetools.api.models.product_type.AttributeType;
import com.commercetools.api.models.product_type.TextInputHint;
import lombok.*;

import java.util.List;

@Data
public class ProductDTO {

    private String productTypeId;
    private String key;
    private String Name;
    private String Description;
    private String slug;
    private long version;
    private Object attributeValue;
    private String attributeName;
    private MasterVariantDTO masterVariant;
    private List<VariantDTO> variants;
    private List<AttributesDTO> attributes;
    private List<PriceDTO> prices;
    private List<ImagesDTO> images;
   // private UpdateActionDTO actions;
}
