package com.CommerceTool.ProductSelection;

import com.commercetools.api.models.product_selection.ProductSelectionMode;
import lombok.Data;

import java.util.List;

@Data
public class ProductSelectionDTO {

    private String key;
    private String name;
    private ProductSelectionMode mode;
    private List<UpdateActionDTO> actions;
    private UpdateActionDTO updateActionDTO;
}
