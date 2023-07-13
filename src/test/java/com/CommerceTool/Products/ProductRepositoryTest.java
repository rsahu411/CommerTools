package com.CommerceTool.Products;

import static org.mockito.ArgumentMatchers.any;

import com.CommerceTool.Product.ProductRepository;
import com.CommerceTool.Product.ProductService;
import com.commercetools.api.client.*;
import com.commercetools.api.models.category.CategoryReference;
import com.commercetools.api.models.category.CategoryReferenceBuilder;
import com.commercetools.api.models.common.*;
import com.commercetools.api.models.product.*;
import com.commercetools.api.models.product_type.*;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProjectApiRoot apiRoot;

    Product product;

    @BeforeEach
    public void dataSetUp()
    {
        product = ProductBuilder.of()
                .key("test-product-key")
                .productType(ProductTypeReferenceBuilder.of()
                        .id("12345678")
                        .buildUnchecked())
                .masterData(ProductCatalogDataBuilder.of()
                        .current(ProductDataBuilder.of()
                                .name(LocalizedString.ofEnglish("Shoes"))
                                .masterVariant(ProductVariantBuilder.of()
                                        .sku("Master-SKU")
                                        .price(PriceBuilder.of()
                                                .country("IN")
                                                .value(TypedMoneyBuilder.of()
                                                        .centPrecisionBuilder()
                                                        .currencyCode("INR")
                                                        .centAmount(1000L)
                                                        .buildUnchecked())
                                                .buildUnchecked())
                                        .attributes(AttributeBuilder.of()
                                                .name("Brand")
                                                .value("Puma")
                                                .buildUnchecked())
                                        .images(ImageBuilder.of()
                                                .url("rishabh.jpg")
                                                .dimensions(ImageDimensionsBuilder.of()
                                                        .w(100).h(100)
                                                        .buildUnchecked())
                                                .buildUnchecked())
                                        .buildUnchecked())
                                .buildUnchecked())
                        .buildUnchecked())
                .buildUnchecked();
    }
    @Test
    public void createProduct()
    {

        ByProjectKeyProductsRequestBuilder byProjectKeyProductsRequestBuilder = Mockito.mock(ByProjectKeyProductsRequestBuilder.class);
        Mockito.when(apiRoot.products()).thenReturn(byProjectKeyProductsRequestBuilder);

        ByProjectKeyProductsPost byProjectKeyProductsPost = Mockito.mock(ByProjectKeyProductsPost.class);
        Mockito.when(byProjectKeyProductsRequestBuilder.post(any(ProductDraft.class))).thenReturn(byProjectKeyProductsPost);

        ApiHttpResponse<Product> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductsPost.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(product);

//        ProductDraft productDraft = new ProductDraftImpl();
        Product actualProduct = productRepository.createProduct(ProductDraft.builder().buildUnchecked());

        Assertions.assertEquals(actualProduct.getKey(),"test-product-key");
    }


    @Test
    public void getAllProduct()
    {
        ProductPagedQueryResponse productPagedQueryResponse = ProductPagedQueryResponseBuilder.of()
                .results(
                    ProductBuilder.of()
                            .productType(ProductTypeReferenceBuilder.of().id("12345").buildUnchecked())
                            .masterData(ProductCatalogDataBuilder.of()
                                    .current(ProductDataBuilder.of()
                                            .name(LocalizedString.ofEnglish("Test_Product"))
                                            .categories(CategoryReferenceBuilder.of().id("12345678").buildUnchecked())
                                            .masterVariant(ProductVariantBuilder.of()
                                                    .sku("MasterSKU")
                                                    .attributes(AttributeBuilder.of().name("Brand").value("Puma").buildUnchecked())
                                                    .images(ImageBuilder.of()
                                                            .url("rishabh.jpg")
                                                            .dimensions(ImageDimensionsBuilder.of().h(100).w(100).buildUnchecked())
                                                            .buildUnchecked())
                                                    .price(PriceBuilder.of()
                                                            .country("IN")
                                                            .value(TypedMoneyBuilder.of()
                                                                    .centPrecisionBuilder()
                                                                    .currencyCode("INR")
                                                                    .centAmount(1000L)
                                                                    .buildUnchecked())
                                                            .buildUnchecked())
                                                    .buildUnchecked())
                                            .buildUnchecked())
                                    .buildUnchecked())
                            .buildUnchecked()
                )
                .buildUnchecked();

        ByProjectKeyProductsRequestBuilder byProjectKeyProductsRequestBuilder = Mockito.mock(ByProjectKeyProductsRequestBuilder.class);
        Mockito.when(apiRoot.products()).thenReturn(byProjectKeyProductsRequestBuilder);

        ByProjectKeyProductsGet byProjectKeyProductsGet = Mockito.mock(ByProjectKeyProductsGet.class);
        Mockito.when(byProjectKeyProductsRequestBuilder.get()).thenReturn(byProjectKeyProductsGet);
        Mockito.when(byProjectKeyProductsGet.withLimit("10")).thenReturn(byProjectKeyProductsGet);

        ApiHttpResponse<ProductPagedQueryResponse> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductsGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(productPagedQueryResponse);

        ProductPagedQueryResponse expectedQueryResponse = productService.getProducts("10");

        Assertions.assertEquals(expectedQueryResponse.getResults().stream().map(result->result.getProductType().getId()).collect(Collectors.toList()),List.of("12345"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().map(result->result.getMasterData().getCurrent().getMasterVariant().getPrice().getValue().getCurrencyCode()).collect(Collectors.toList()), List.of("INR"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().map(result->result.getMasterData().getCurrent().getMasterVariant().getPrice().getValue().getCentAmount()).collect(Collectors.toList()), List.of(1000L));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().map(result->result.getMasterData().getCurrent().getMasterVariant().getPrice().getCountry()).collect(Collectors.toList()), List.of("IN"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getMasterVariant().getImages().stream().map(Image::getUrl)).collect(Collectors.toList()), List.of("rishabh.jpg"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getMasterVariant().getImages().stream().map(image -> image.getDimensions().getH())).collect(Collectors.toList()), List.of(100));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getMasterVariant().getImages().stream().map(image -> image.getDimensions().getW())).collect(Collectors.toList()), List.of(100));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getMasterVariant().getAttributes().stream().map(Attribute::getValue)).collect(Collectors.toList()), List.of("Puma"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getMasterVariant().getAttributes().stream().map(Attribute::getName)).collect(Collectors.toList()), List.of("Brand"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().flatMap(result->result.getMasterData().getCurrent().getCategories().stream().map(CategoryReference::getId)).collect(Collectors.toList()), List.of("12345678"));
        Assertions.assertEquals(expectedQueryResponse.getResults().stream().map(result->result.getMasterData().getCurrent().getName()).collect(Collectors.toList()),List.of(LocalizedString.ofEnglish("Test_Product")));
    }


    @Test
    public void getProductById()
    {
        ByProjectKeyProductsRequestBuilder byProjectKeyProductsRequestBuilder = Mockito.mock(ByProjectKeyProductsRequestBuilder.class);
        Mockito.when(apiRoot.products()).thenReturn(byProjectKeyProductsRequestBuilder);

        ByProjectKeyProductsByIDRequestBuilder byProjectKeyProductsByIDRequestBuilder = Mockito.mock(ByProjectKeyProductsByIDRequestBuilder.class);
        Mockito.when(byProjectKeyProductsRequestBuilder.withId("123")).thenReturn(byProjectKeyProductsByIDRequestBuilder);

        ByProjectKeyProductsByIDGet byProjectKeyProductsByIDGet = Mockito.mock(ByProjectKeyProductsByIDGet.class);
        Mockito.when(byProjectKeyProductsByIDRequestBuilder.get()).thenReturn(byProjectKeyProductsByIDGet);

        ApiHttpResponse<Product> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductsByIDGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(product);

        Product actualProduct = productService.getProductById("123");
        System.out.println(actualProduct.getMasterData().getCurrent().getMasterVariant().getPrice().getCountry());

        Assertions.assertEquals(product.getKey(),actualProduct.getKey());
    }


    @Test
    public void deleteById(){

    }
}
