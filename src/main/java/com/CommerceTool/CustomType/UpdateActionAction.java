package com.CommerceTool.CustomType;

import lombok.Data;

@Data
public class UpdateActionAction extends CustomFieldDTO {

    private String action;
    private String fieldName;

}
