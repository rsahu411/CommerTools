package com.CommerceTool.ProductType;

import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product_type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository repository;
    @Autowired
    private ProjectApiRoot apiRoot;


    // Create Product Type
    public ProductType createProductType(ProductTypeDTO productTypeDTO)
    {
        List<AttributeDefinitionDraft> attributeList = productTypeDTO
                .getAttributesDetails()
                .stream().map(attributesDetails -> {

                    return      AttributeDefinitionDraft
                            .builder()
                        //    .type(AttributeType.textBuilder().build())
                            .type(getAttributeType(attributesDetails))
                            .name(attributesDetails.getName())
                            .isRequired(attributesDetails.getIsRequired())
                            .label(LocalizedString.ofEnglish(attributesDetails.getLabel()))
                            .isSearchable(attributesDetails.getIsSearchable())
                            .attributeConstraint(attributesDetails.getAttributeConstraint())
                            .inputHint(attributesDetails.getInputHint())
                            .build();
                }).collect(Collectors.toList()) ;

//        List<AttributeDefinitionDraft> nestedAttribute = List.of(
//                AttributeDefinitionDraft
//                        .builder()
//                        .name(productTypeDTO.getAttributeName())
//                        .type(productTypeDTO.getAttributeType())
//                        .isRequired(productTypeDTO.getIsRequired())
//                        .isSearchable(productTypeDTO.getIsSearchable())
//                        .label(LocalizedString.ofEnglish(productTypeDTO.getLabel()))
//                        .attributeConstraint(productTypeDTO.getAttributeConstraint())
//                        .inputHint(productTypeDTO.getInputHint())
//                        .build()
//        );

        ProductTypeDraft productType = ProductTypeDraft
                .builder()
                .name(productTypeDTO.getName())
                .description(productTypeDTO.getDescription())
                // .attributes(nestedAttribute)
                .attributes(attributeList)
                .key(productTypeDTO.getKey())
                .build();

        return repository.createProductType(productType);
    }




    public AttributeType getAttributeType(AttributesDTO attributesDTO)
    {
        return switch (attributesDTO.getType())
                {
                    case "text"->AttributeType.textBuilder().build();
                    case "number"->AttributeType.numberBuilder().build();
                    case "boolean"->AttributeType.booleanBuilder().build();
                    case "date"->AttributeType.dateBuilder().build();
                    case "money"->AttributeType.moneyBuilder().build();
                    default -> throw new InvalidActionException(attributesDTO.getType());
                };
    }





    // Get All Product Type
    public ProductTypePagedQueryResponse getAllProductTypes(String limit)
    {

        ProductTypePagedQueryResponse productType = apiRoot
                .productTypes()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return productType;
    }




    // Get Product-Type By ID
    public ProductType getProductTypeById(String id)
    {
        ProductType productType = apiRoot
                .productTypes()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
        return productType;
    }


    // Delete Product-Type By ID
    public ProductType deleteProductTypeById(String id,String version)
    {
        ProductType productType = apiRoot
                .productTypes()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
        return productType;
    }




    // update Product Type

    public ProductType productTypeUpdate(String id, ProductTypeDTO productTypeDTO)
    {
        ProductType product = apiRoot.productTypes().withId(id).get().executeBlocking().getBody();

        List<ProductTypeUpdateAction> productTypeUpdateActions = productTypeDTO.getActions().stream()
                .map(actionDTO ->
                {
                    return switch (actionDTO.getAction())
                            {
                                case "addAttributeDefinition"->ProductTypeUpdateAction
                                        .addAttributeDefinitionBuilder()
                                        .attribute(AttributeDefinitionDraft
                                                .builder()
                                                .type(getAttributeType(actionDTO.getTypes()))
                                                .name(actionDTO.getName())
                                                .label(LocalizedString.ofEnglish(actionDTO.getLabel()))
                                                .isRequired(actionDTO.getIsRequired())
                                                .attributeConstraint(actionDTO.getAttributeConstraint())
                                                .inputHint(actionDTO.getInputHint())
                                                .build())
                                        .build();

                                case "removeAttributeDefinition" -> ProductTypeUpdateAction
                                        .removeAttributeDefinitionBuilder()
                                        .name(actionDTO.getName())
                                        .build();
                                default -> throw new InvalidActionException(actionDTO.getAction());
                            };
                })
                .collect(Collectors.toList());

//        List<ProductTypeUpdateAction> streamOfUpdateAttribute = productTypeDTO
//                .getAttributesDetails()
//                .stream()
//                .map(e->{
//                    return ProductTypeUpdateActionBuilder
//                            .of()
//                            .addAttributeDefinitionBuilder()
//                            .attribute(AttributeDefinitionDraft.builder()
//                                    .type(e.getAttributeType())
//                                    .label(LocalizedString.ofEnglish(e.getLabel()))
//                                    .inputHint(e.getInputHint())
//                                    .name(e.getAttributeName())
//                                    .attributeConstraint(e.getAttributeConstraint())
//                                    .isSearchable(e.getIsSearchable())
//                                    .isRequired(e.getIsRequired())
//                                    .build())
//                            .build();}).collect(Collectors.toList());
        ProductTypeUpdate productTypeUpdate = ProductTypeUpdate
                .builder()
                .version(product.getVersion())
                .actions(productTypeUpdateActions)
                .build();


        return apiRoot
                .productTypes()
                .withId(product.getId())
                .post(productTypeUpdate)
                .executeBlocking()
                .getBody();
    }
}
