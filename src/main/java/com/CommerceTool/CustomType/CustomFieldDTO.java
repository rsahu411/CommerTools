package com.CommerceTool.CustomType;

import com.commercetools.api.models.type.CustomFieldEnumValue;
import com.commercetools.api.models.type.FieldType;
import com.commercetools.api.models.type.TypeTextInputHint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomFieldDTO implements Serializable {
    private String key;
    private String type;
    private String name;
    private String label;
    private Boolean required;
    private TypeTextInputHint inputHint;
    List<EnumValuesDTO> values;
}
