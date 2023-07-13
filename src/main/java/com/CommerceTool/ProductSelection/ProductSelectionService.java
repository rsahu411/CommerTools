package com.CommerceTool.ProductSelection;

import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.ProductResourceIdentifier;
import com.commercetools.api.models.product_selection.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductSelectionService {

    @Autowired
    private ProductSelectionRepository repository;
    @Autowired
    private ProjectApiRoot apiRoot;

    // Create  Product-Selection
    public ProductSelection createProductSelection(ProductSelectionDTO productSelectionDTO) {

        ProductSelectionDraft productSelectionDraft = ProductSelectionDraft
                .builder()
                .key(productSelectionDTO.getKey())
                .name(LocalizedString.of(Locale.US,productSelectionDTO.getName()))
                .mode(productSelectionDTO.getMode())
                .build();
        return repository.createProductSelection(productSelectionDraft);
    }




    // Update Product-Selection By id
    public ProductSelection updateProductSelection(ProductSelectionDTO productSelectionDTO, String productSelectionId)
    {
        List<ProductSelectionUpdateAction> updateActions = productSelectionDTO.getActions()
                .stream()
                .map(actionDTO -> {
                    return switch (actionDTO.getAction())
                            {
                                case "addProduct" -> ProductSelectionUpdateAction
                                        .addProductBuilder()
                                        .product(addProduct(actionDTO))
                                        .variantSelection(addVariant(actionDTO))
                                        .build();
                                case "removeProduct" -> ProductSelectionUpdateAction
                                        .removeProductBuilder()
                                        .product(addProduct(actionDTO))
                                        .build();
                                default -> throw new InvalidActionException(actionDTO.getAction());

                            };
                })
                .collect(Collectors.toList());

        ProductSelection productSelection = apiRoot.productSelections().withId(productSelectionId).get().executeBlocking().getBody();

        ProductSelectionUpdate productSelectionUpdate = ProductSelectionUpdate
                .builder()
                .version(productSelection.getVersion())
                .actions(updateActions)
                .build();

        ProductSelection updatedProductSelection = apiRoot
                .productSelections()
                .withId(productSelection.getId())
                .post(productSelectionUpdate)
                .executeBlocking()
                .getBody();

        return updatedProductSelection;
    }





    // Add product
    public ProductResourceIdentifier addProduct(UpdateActionDTO updateActionDTO)
    {
        ProductResourceIdentifier productResourceIdentifier = ProductResourceIdentifier
                .builder()
                .id(updateActionDTO.getId())
                .build();
        return productResourceIdentifier;
    }




    // Add Variant
    public ProductVariantSelection addVariant(UpdateActionDTO updateActionDTO)
    {
      return   switch (updateActionDTO.getType())
        {
            case "inclusion" -> ProductVariantSelection
                    .inclusionBuilder()
                    .skus(updateActionDTO.getSkus())
                    .build();
            case "exclusion" -> ProductVariantSelection
                    .exclusionBuilder()
                    .skus(updateActionDTO.getSkus())
                    .build();
            case "includeOnly" -> ProductVariantSelection
                    .includeOnlyBuilder()
                    .skus(updateActionDTO.getSkus())
                    .build();
            case "includeAllExcept" -> ProductVariantSelection
                    .includeAllExceptBuilder()
                    .skus(updateActionDTO.getSkus())
                    .build();
            default -> throw new RuntimeException("Invalid Input");
        };
    }





    // Query Product-Selections
    public ProductSelectionPagedQueryResponse getAllProductSelection(String limit)
    {
        ProductSelectionPagedQueryResponse queryResponse = apiRoot
                .productSelections()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return queryResponse;
    }




    // Get Product-Selection By Id
    public ProductSelection getProductSelectionById(String productSelectionId)
    {
        ProductSelection productSelection = apiRoot
                .productSelections()
                .withId(productSelectionId)
                .get()
                .executeBlocking()
                .getBody();
        return productSelection;
    }



    // Delete Product-Selection By Id
    public ProductSelection deleteProductSelectionById(String productSelectionId,String version)
    {
        ProductSelection productSelection = apiRoot
                .productSelections()
                .withId(productSelectionId)
                .delete(version)
                .executeBlocking()
                .getBody();
        return productSelection;
    }
}
