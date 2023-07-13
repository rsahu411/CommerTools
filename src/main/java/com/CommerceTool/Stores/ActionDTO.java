package com.CommerceTool.Stores;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {

    private String action;
    private String code;
    private String id;
    private Boolean active;
}
