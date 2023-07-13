package com.CommerceTool.Cart;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartRepository {

    @Autowired
    private ProjectApiRoot apiRoot;

    public Cart createCart(CartDraft cart) {
        return  apiRoot
                .carts()
                .post(cart)
                .executeBlocking()
                .getBody();
    }
}
