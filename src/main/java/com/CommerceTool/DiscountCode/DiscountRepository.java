package com.CommerceTool.DiscountCode;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart_discount.CartDiscount;
import com.commercetools.api.models.cart_discount.CartDiscountDraft;
import com.commercetools.api.models.discount_code.DiscountCode;
import com.commercetools.api.models.discount_code.DiscountCodeDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountRepository {

    @Autowired
    private ProjectApiRoot apiRoot;

    public DiscountCode createDiscountCode(DiscountCodeDraft codeDraft) {
        return apiRoot.discountCodes().post(codeDraft).executeBlocking().getBody();
    }

    public CartDiscount createCartDiscount(CartDiscountDraft cartDiscountDraft) {
        return apiRoot.cartDiscounts().post(cartDiscountDraft).executeBlocking()
                .getBody();
    }
}
