package com.CommerceTool.ProductType;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product_type.ProductType;
import com.commercetools.api.models.product_type.ProductTypeDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypeRepository {
    @Autowired
    private ProjectApiRoot apiRoot;

    public ProductType createProductType(ProductTypeDraft productType) {
        return  apiRoot
                .productTypes()
                .post(productType)
                .executeBlocking()
                .getBody();
    }
}
