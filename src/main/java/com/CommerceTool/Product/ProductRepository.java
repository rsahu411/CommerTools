package com.CommerceTool.Product;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product_type.ProductType;
import com.commercetools.api.models.product_type.ProductTypeDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Product createProduct(ProductDraft productDraft) {
        return  apiRoot
                .products()
                .post(productDraft)
                .executeBlocking()
                .getBody();
    }


}
