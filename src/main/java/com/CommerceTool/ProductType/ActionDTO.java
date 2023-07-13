package com.CommerceTool.ProductType;

import com.commercetools.api.models.product_type.AttributeConstraintEnum;
import com.commercetools.api.models.product_type.AttributeType;
import com.commercetools.api.models.product_type.TextInputHint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {

    private String action;
    private String label;
    private String name;
    private String attributeName;
    private AttributeConstraintEnum attributeConstraint;
    private TextInputHint inputHint;
    private String  type;
    private Boolean isSearchable;
    private Boolean isRequired;
    private AttributesDTO types;
}
