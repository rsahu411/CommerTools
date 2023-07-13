package com.CommerceTool.ProductSelection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActionDTO {

    private String action;
    private String id;
    private String type;
    private List<String> skus;
}
