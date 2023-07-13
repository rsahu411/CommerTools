package com.CommerceTool.Product;

import com.commercetools.api.models.product_type.AttributeConstraintEnum;
import com.commercetools.api.models.product_type.AttributeType;
import com.commercetools.api.models.product_type.TextInputHint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class AttributesDTO {

    private String name;
    private Object value;
}
