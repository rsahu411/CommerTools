package com.CommerceTool.ProductType;

import com.commercetools.api.models.product_type.AttributeConstraintEnum;
import com.commercetools.api.models.product_type.AttributeType;
import com.commercetools.api.models.product_type.TextInputHint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributesDTO {

    private String label;

    private String name;
    private AttributeConstraintEnum attributeConstraint;
    private TextInputHint inputHint;
    private String type;
    private Boolean isSearchable;
    private Boolean isRequired;

}
