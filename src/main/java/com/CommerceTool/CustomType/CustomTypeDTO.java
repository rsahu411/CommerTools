package com.CommerceTool.CustomType;


import com.commercetools.api.models.type.ResourceTypeId;
import com.commercetools.api.models.type.TypeTextInputHint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomTypeDTO {

    private long version;
    private String name;
    private String description;
    private String key;
    private List<ResourceTypeId> resourceTypeIds;
    private List<CustomFieldDTO> fieldDefinitions;
    private List<UpdateActionAction> actions;
}
