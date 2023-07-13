package com.CommerceTool.ProductSelection;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product_selection.ProductSelection;
import com.commercetools.api.models.product_selection.ProductSelectionDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSelectionRepository {

    @Autowired
    private ProjectApiRoot apiRoot;

    public ProductSelection createProductSelection(ProductSelectionDraft productSelectionDraft)
    {
        return apiRoot
                .productSelections()
                .post(productSelectionDraft)
                .executeBlocking()
                .getBody();
    }
}
