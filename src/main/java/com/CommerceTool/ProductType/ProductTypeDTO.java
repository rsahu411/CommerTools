package com.CommerceTool.ProductType;

import com.commercetools.api.models.product_type.AttributeConstraintEnum;
import com.commercetools.api.models.product_type.AttributeType;
import com.commercetools.api.models.product_type.TextInputHint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeDTO {


    private String name;
    private String description;
    private String key;
    private List<AttributesDTO> attributesDetails;
    private List<ActionDTO> actions;

}
