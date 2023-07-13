package com.CommerceTool.Product;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.WithKey;
import com.commercetools.api.models.common.*;
import com.commercetools.api.models.product.*;
import com.commercetools.api.models.product_type.*;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProjectApiRoot projectApiRoot;
    @Autowired
    ProductRepository repository;



    // Create Product
    public Product createProduct(@NotNull ProductDTO productDTO)
    {

        ProductDraft productDraft = ProductDraft
                .builder()
                .productType(ProductTypeResourceIdentifier
                        .builder()
                        .id(productDTO.getProductTypeId())
                        .build())
                .name(LocalizedString.ofEnglish(productDTO.getName()))
                .description(LocalizedString.ofEnglish(productDTO.getDescription()))
                .key(productDTO.getKey())
                .slug(LocalizedString.ofEnglish(productDTO.getSlug()))
                .masterVariant(ProductVariantDraftBuilder
                        .of()
                        .key(productDTO.getMasterVariant().getKey())
                        .sku(productDTO.getMasterVariant().getSku())
                        .prices(createPrice(productDTO.getMasterVariant()))
                        .attributes(setAttributeValues(productDTO.getMasterVariant()))
                        .images(Image.builder().build())
                        .images(createImage(productDTO.getMasterVariant()))
                        .build())
                .variants(createVariant(productDTO))
                .build();

        return repository.createProduct(productDraft);
    }


    // Create Variant Array Of Product
    public List<ProductVariantDraft> createVariant(ProductDTO productDTO)
    {
        List<ProductVariantDraft> productVariants = productDTO.getVariants()
                .stream()
                .map(variantDTO -> {
                    return ProductVariantDraft
                            .builder()
                            .key(variantDTO.getKey())
                            .sku(variantDTO.getSku())
                            .prices(variantDTO.getPrices().stream()
                                    .map(priceDTO -> {
                                        return PriceDraft
                                                .builder()
                                                .key(priceDTO.getKey())
                                                .country(priceDTO.getCountry())
                                                .value(Money.builder()
                                                        .currencyCode(priceDTO.getCurrencyCode())
                                                        .centAmount(priceDTO.getCentAmount())
                                                        .build())
                                                .build();
                                    }).collect(Collectors.toList()))

                            .images(variantDTO.getImages().stream()
                                    .map(imagesDTO -> {
                                        return Image
                                                .builder()
                                                .url(imagesDTO.getUrl())
                                                .label(imagesDTO.getLabel())
                                                .dimensions(ImageDimensions
                                                        .builder()
                                                        .w(imagesDTO.getW())
                                                        .h(imagesDTO.getH())
                                                        .build())
                                                .build();
                                    })
                                    .collect(Collectors.toList()))

                            .attributes(variantDTO.getAttributes().stream()
                                    .map(attributesDTO -> {
                                        return Attribute
                                                .builder()
                                                .name(attributesDTO.getName())
                                                .value(attributesDTO.getValue())
                                                .build();
                                    })
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
        return productVariants;
    }



    // Create Price for Variants
    public List<PriceDraft> createPrice(MasterVariantDTO masterVariantDTO)
    {
        List<PriceDraft> priceDrafts = masterVariantDTO.getPrices()
                .stream()
                .map(priceDTO -> {
                    return PriceDraft
                            .builder()
                            .key(priceDTO.getKey())
                            .country(priceDTO.getCountry())
                            .value(Money
                                    .builder()
                                    .currencyCode(priceDTO.getCurrencyCode())
                                    .centAmount(priceDTO.getCentAmount())
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());
        return priceDrafts;
    }



    // Set Attributes Value In Project
    public List<Attribute> setAttributeValues(MasterVariantDTO masterVariantDTO)
    {
        List<Attribute> attributes = masterVariantDTO.getAttributes()
                .stream()
                .map(attributesDTO -> {
                    return Attribute
                            .builder()
                            .name(attributesDTO.getName())
                            .value(attributesDTO.getValue())
                            .build();
                })
                .collect(Collectors.toList());
        return attributes;
    }



    // Create Image of Variants
    public List<Image> createImage(MasterVariantDTO masterVariantDTO)
    {
        List<Image> imageBuilders = masterVariantDTO.getImages()
                .stream()
                .map(imagesDTO -> {
                    return ImageBuilder
                            .of()
                            .url(imagesDTO.getUrl())
                            .label(imagesDTO.getLabel())
                            .dimensions(ImageDimensionsBuilder.of()
                                    .h(imagesDTO.getH())
                                    .w(imagesDTO.getW())
                                    .build())
                            .build();

                })
                .collect(Collectors.toList());
        return imageBuilders;
    }




    // Update Product
    public Product UpdateProduct(String id, ProductDTO productDTO)
    {
        Product product = projectApiRoot
                .products()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();

        ProductUpdate productUpdate = ProductUpdate
                .builder()
                .version(product.getVersion())
                .plusActions(ProductUpdateAction.addVariantBuilder()
                        .plusAttributes(Attribute
                                .builder()
                                .name(productDTO.getName())
                                .value(productDTO.getAttributeValue())
                                .build())
                        .build())
                .build();

        Product updatedProduct= projectApiRoot
                .products()
                .withId(product.getId())
                .post(productUpdate)
                .executeBlocking()
                .getBody();
        return updatedProduct;
    }

//    public Product updateActions(ProductDTO productDTO)
//    {
//
//        switch(productDTO.getActions().getAction())
//        {
//            case "addPrice" : PriceDraft
//                    .builder()
//                    .key()
//                    .build();
//        }
//    }

    // Get Product By id
    public Product getProductById(String id) {

        Product product=projectApiRoot
                .products()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
        return  product;
    }




    // Get All Product
    public CompletableFuture<ApiHttpResponse<ProductPagedQueryResponse>> getProducts(String limit) {

        return projectApiRoot
                .products()
                .get()
                .withLimit(limit)
                .execute();
//                .executeBlocking()
//                .getBody();
    }



    public long getPrice(String id)
    {
        long price = projectApiRoot
                .products()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody()
                .getMasterData().getCurrent().getVariants().get(0)
                .getPrices().get(0).getValue().getCentAmount();
        return price;
    }




    // Delete Products
    public Product deleteProducts(String id,long version) {

        Product product = projectApiRoot
                .products()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
        return product;
    }




    public Object getAttributeValue(String id)
    {
        Product product = projectApiRoot
                    .products()
                    .withId(id)
                    .get()
                    .executeBlocking()
                    .getBody();
       return  product.getMasterData().getCurrent().getMasterVariant().getAttributes().get(0).getValue();
    }
}
