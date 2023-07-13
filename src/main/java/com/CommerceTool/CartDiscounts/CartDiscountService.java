package com.CommerceTool.CartDiscounts;

import com.CommerceTool.DiscountCode.DiscountRepository;
import com.CommerceTool.configuration.Client;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart_discount.*;
import com.commercetools.api.models.common.LocalizedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDiscountService {

    @Autowired
    private ProjectApiRoot projectApiRoot;
    @Autowired
    private DiscountRepository repository;
    public CartDiscount createCartDiscount(CartDiscountDTO cartDiscountDTO)

    {
//        CartDiscountValueDraft abslute = null;
//        CartDiscountValueDraft relative=null;
//
//        if(cartDiscountDetails.getType().equals(abslute)){
//            String df=cartDiscountValueDraftBuilder -> cartDiscountValueDraftBuilder.absoluteBuilder();
//        } else if (cartDiscountDetails.getType().equals(relative)) {
//            cartDiscountValueDraftBuilder -> cartDiscountValueDraftBuilder.relativeBuilder();
//        }

        CartDiscountDraft cartDiscountDraft = CartDiscountDraft
                .builder()
                .name(LocalizedString.ofEnglish(cartDiscountDTO.getName()))
                .description(LocalizedString.ofEnglish(cartDiscountDTO.getDescription()))
                .validFrom(cartDiscountDTO.getValidFrom())
                .validUntil(cartDiscountDTO.getValidUntil())
                .key(cartDiscountDTO.getKey())
                .requiresDiscountCode(cartDiscountDTO.getRequiresDiscountCode())
                .sortOrder(cartDiscountDTO.getSortOrder())
                .cartPredicate(cartDiscountDTO.getCartPredicate())
                .target(CartDiscountTarget.lineItemsBuilder().predicate(cartDiscountDTO.getTargetPredicate()).build())

//                .value(CartDiscountValueDraft.absoluteBuilder().money(CentPrecisionMoney
//                                .builder().centAmount(cartDiscountDetails.getCentAmount())
//                                .currencyCode(cartDiscountDetails.getCurrencyCode()).build()).build())
                .value(CartDiscountValueDraft.relativeBuilder().permyriad(cartDiscountDTO.getPermyriad()).build())
                .build();

        return  repository.createCartDiscount(cartDiscountDraft);
    }
}
