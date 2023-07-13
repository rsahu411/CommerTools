package com.CommerceTool.Cart;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.channel.ChannelResourceIdentifierBuilder;
import com.commercetools.api.models.common.BaseAddress;
import com.commercetools.api.models.discount_code.DiscountCodeReference;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private ProjectApiRoot apiRoot;
    @Autowired
    private CartRepository repository;


    public Cart createCart(CartDTO cartDTO) {



                CartDraft cart = CartDraft
                        .builder()
                        .key(cartDTO.getKey())
                        .customerId(cartDTO.getCustomerId())
                        .customerEmail(cartDTO.getEmail())
                        .currency(cartDTO.getCurrency())
                    //    .lineItems(createLineItem(cartDTO))
                        .taxMode(cartDTO.getTaxMode())
                        .origin(CartOrigin.CUSTOMER)
                        .build();

        return  repository.createCart(cart);
    }



    // Create Line Item
    public List<LineItemDraft> createLineItem(CartDTO cartDTO)
    {
        List<LineItemDraft> lineItemList = cartDTO
                .getLineItemDetails()
                .stream()
                .map(item -> {
                    return    LineItemDraft
                            .builder()
                            .sku(item.getSku())
                            .quantity(item.getQuantity())
                            .build();
                }).collect(Collectors.toList());
        return lineItemList;
    }


    // Update Add Line Item in Cart
    public Cart addLineItem(CartDTO cartDTO, String id)
    {
        Cart cart = apiRoot.carts().withId(id).get().executeBlocking().getBody();


        CartUpdate cartUpdate = CartUpdate
                .builder()
                .version(cart.getVersion())
                .actions(CartUpdateActionBuilder
                        .of()
                        .addLineItemBuilder()
                        .productId(cartDTO.getProductId())
                        .variantId(cartDTO.getVariantId())
                        .quantity(cartDTO.getQuantity())
                        .supplyChannel(ChannelResourceIdentifierBuilder.of().id(cartDTO.getSupplyId()).build())
                        .distributionChannel(ChannelResourceIdentifierBuilder.of().id(cartDTO.getDistributionId()).build())
                        .build())

                .build();

        Cart updatedCart = apiRoot
                .carts()
                .withId(cart.getId())
                .post(cartUpdate)
                .executeBlocking()
                .getBody();
        return updatedCart;
    }




    // Add Shipping Address in Cart
    public Cart addShippingAddress(CartDTO cartDTO, String cartId)
    {
        Cart cart = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();

        CartUpdate cartUpdate = CartUpdate
                .builder()
                .version(cart.getVersion())
                .actions(CartUpdateActionBuilder.of().setShippingAddressBuilder()
                        .address(BaseAddress.builder()
                                .title(cartDTO.getTitle())
                                .salutation(cartDTO.getSalutation())
                                .firstName(cartDTO.getFirstName())
                                .lastName(cartDTO.getLastName())
                                .streetNumber(cartDTO.getStreetNumber())
                                .streetName(cartDTO.getStreetName())
                                .postalCode(cartDTO.getPostalCode())
                                .city(cartDTO.getCity())
                                .region(cartDTO.getRegion())
                                .state(cartDTO.getState())
                                .country(cartDTO.getCountry())
                                .pOBox(cartDTO.getPOBox())
                                .key(cartDTO.getKey())
                                .build())
                        .build())
                .build();

        Cart updatedCart = apiRoot
                .carts()
                .withId(cart.getId())
                .post(cartUpdate)
                .executeBlocking()
                .getBody();
        return updatedCart;
    }





    // Remove Discount Code
    public Cart removeDiscountCode(CartDTO cartDTO, String cartId)
    {
        Cart cart = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();

        CartUpdate cartUpdate = CartUpdate
                .builder()
                .version(cart.getVersion())
                .actions(CartUpdateActionBuilder
                        .of()
                        .removeDiscountCodeBuilder()
                        .discountCode(DiscountCodeReference.builder().id(cartDTO.getDiscountCodeId()).build())
                        .build())
                .build();

        Cart updatedCart = apiRoot
                .carts()
                .withId(cart.getId())
                .post(cartUpdate)
                .executeBlocking()
                .getBody();
        return updatedCart;
    }




    // Get All Carts
    public CartPagedQueryResponse getAllCarts(String limit) {


        CartPagedQueryResponse queryResponse = apiRoot
                .carts()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return queryResponse;
    }



    // Get Cart By Id
    public Product getCartById(String id) {

        Cart cart = apiRoot
                .carts()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
        String id1 = cart.getLineItems().get(0).getProductId();
        Product product = apiRoot.products().withId(id1).get().executeBlocking().getBody();
        return product;
    }




    // Get Cart By CustomerId
    public Cart GetCartByCustomerId(String customerId)
    {
        Cart cart = apiRoot
                .carts()
                .withCustomerId(customerId)
                .get()
                .executeBlocking()
                .getBody();

        return cart;
    }



    // Delete Cart By Id
    public Cart deleteCartById(String id,long version) {

        Cart cart = apiRoot
                .carts()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
        return cart;

    }



    // Get Quantity
    public long getQuantity(String id) {

        Cart cart=apiRoot.carts().withId(id).get().executeBlocking().getBody();
        long quantity=cart.get().getLineItems().get(0).getQuantity();
        return quantity;

    }

    public Product getProductByOrderId(String orderId)
    {
        Order order =apiRoot.orders().withId(orderId).get().executeBlocking().getBody();
        String productId = order.getLineItems().get(0).getProductId();
        System.out.println(productId);
        Product product = apiRoot.products().withId(productId).get().executeBlocking().getBody();
        return product;
    }

}
