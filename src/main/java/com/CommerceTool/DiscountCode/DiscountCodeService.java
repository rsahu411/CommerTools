package com.CommerceTool.DiscountCode;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart_discount.*;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.discount_code.DiscountCode;
import com.commercetools.api.models.discount_code.DiscountCodeDraft;
import com.commercetools.api.models.discount_code.DiscountCodePagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountCodeService {

    @Autowired
    private ProjectApiRoot projectApiRoot;
    @Autowired
    private DiscountRepository repository;
    public DiscountCode createDiscountCode(DiscountCodeDTO discountCodeDTO) {
        List<CartDiscountResourceIdentifier> cartList = discountCodeDTO.getCartDiscountVariables()
                .stream()
                .map(details->CartDiscountResourceIdentifier.builder().id(details.getId()).build())
                .collect(Collectors.toList());

        DiscountCodeDraft codeDraft = DiscountCodeDraft
                .builder()
                .name(LocalizedString.ofEnglish(discountCodeDTO.getName()))
                .description(LocalizedString.ofEnglish(discountCodeDTO.getDescription()))
                .code(discountCodeDTO.getCode())
                .validFrom(discountCodeDTO.getValidFrom())
                .validUntil(discountCodeDTO.getValidUntil())
                .isActive(discountCodeDTO.getIsActive())
                .maxApplications(discountCodeDTO.getMaxApplication())
                .maxApplicationsPerCustomer(discountCodeDTO.getMaxApplicationPerCustomer())
                .cartDiscounts(cartList)
                .build();
        return repository.createDiscountCode(codeDraft);
    }



    public DiscountCodePagedQueryResponse getAllDiscountCode(String limit)
    {
        DiscountCodePagedQueryResponse queryResponse= projectApiRoot
                .discountCodes()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return queryResponse;
    }


    public DiscountCode getDiscountCodeById(String id)
    {
         DiscountCode discountCode = projectApiRoot
                 .discountCodes()
                 .withId(id)
                 .get()
                 .executeBlocking()
                 .getBody();
         return discountCode;
    }

    public DiscountCode deleteDiscountCodeById(String id, Long version) {

        DiscountCode discountCode = projectApiRoot
                .discountCodes()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
        return discountCode;
    }
}
