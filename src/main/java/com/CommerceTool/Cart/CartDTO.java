package com.CommerceTool.Cart;

import com.commercetools.api.models.cart.TaxMode;
import com.commercetools.api.models.common.LocalizedString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private String key;
    private String email;
    private String currency;
    private TaxMode taxMode;
    private String customerId;
    private long version;
    private String lineItemId;
    private long quantity;
    private String supplyId;
    private String distributionId;

    private LocalizedString customFieldName;
    private List<LineItemDTO> lineItemDetails;

    private String discountCodeId;




    // Shipping Address Details
    private String title;
    private String salutation;
    private String firstName;
    private String lastName;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String state;
    private String region;
    private String country;
    private String pOBox;



    // Line Item Details
    private String productId;
    private Long variantId;
    private String supplyChannelId;
    private String distributionChannelId;
    private String sku;

}
