package com.CommerceTool.Products;

import com.CommerceTool.Product.ProductRepository;
import com.CommerceTool.Product.ProductService;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.MoneyBuilder;
import com.commercetools.api.models.common.PriceDraftBuilder;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductBuilder;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product.ProductVariantDraftBuilder;
import com.commercetools.api.models.product_type.ProductTypeReference;
import com.commercetools.api.models.product_type.ProductTypeReferenceBuilder;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifier;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.PrivateKey;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository repository;

    @Test
    public void createProductTest()
    {

//        ProductDraft product = ProductDraft
//                .builder()
//                .productType(ProductTypeResourceIdentifierBuilder.of().id("12345").buildUnchecked())
//                .name(LocalizedString.ofEnglish("Shoes"))
//                .description(LocalizedString.ofEnglish("This is shoes"))
//                .key("shoeKey")
//                .masterVariant(ProductVariantDraftBuilder
//                        .of()
//                        .sku("MasterShoes")
//                        .key("masterShoeKey")
//                        .prices(PriceDraftBuilder.of()
//                                .country("IN")
//                                .value(MoneyBuilder
//                                        .of()
//                                        .centAmount(10000L)
//                                        .currencyCode("INR")
//                                        .buildUnchecked())
//                                .buildUnchecked())
//                        .buildUnchecked())
//                .buildUnchecked();

        Product product = ProductBuilder
                .of()
                .productType(ProductTypeReferenceBuilder.of().buildUnchecked())
                .buildUnchecked();


       // Mockito.when(repository.createProduct(any())).thenReturn(product);
    }
}
