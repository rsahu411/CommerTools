package com.CommerceTool.ProductType;


import static org.mockito.ArgumentMatchers.any;

import com.commercetools.api.client.*;
import com.commercetools.api.models.common.LocalizedString;
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
public class ProductTypeRepositoryTest {

    @InjectMocks
    private ProductTypeRepository productTypeRepository;
    @InjectMocks
    private ProductTypeService productTypeService;
    @Mock
    private ProjectApiRoot apiRoot;

    ProductType productType;
    @BeforeEach
    public void setUp()
    {
       productType = ProductTypeBuilder.of()
               .id("12345")
               .key("test-product-type")
               .name("Test-Name")
               .attributes(List.of(AttributeDefinitionBuilder.of()
                       .name("Test-Attribute")
                       .isRequired(true)
                       .label(LocalizedString.ofEnglish("Test-Label"))
                       .buildUnchecked()))
               .buildUnchecked();
    }



    @Test
    public void createProductTypeTest()
    {
        ByProjectKeyProductTypesRequestBuilder byProjectKeyProductTypesRequestBuilder = Mockito.mock(ByProjectKeyProductTypesRequestBuilder.class);
        Mockito.when(apiRoot.productTypes()).thenReturn(byProjectKeyProductTypesRequestBuilder);

        ByProjectKeyProductTypesPost byProjectKeyProductTypesPost = Mockito.mock(ByProjectKeyProductTypesPost.class);
        Mockito.when(byProjectKeyProductTypesRequestBuilder.post(any(ProductTypeDraft.class))).thenReturn(byProjectKeyProductTypesPost);

        ApiHttpResponse<ProductType> productTypeApiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductTypesPost.executeBlocking()).thenReturn(productTypeApiHttpResponse);
        Mockito.when(productTypeApiHttpResponse.getBody()).thenReturn(productType);

        ProductType expectedProductType = productTypeRepository.createProductType(ProductTypeDraft.builder().buildUnchecked());

        Assertions.assertEquals(expectedProductType.getKey(),"test-product-type");
        Assertions.assertEquals(expectedProductType.getName(),"Test-Name");
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getName).collect(Collectors.toList()), List.of("Test-Attribute") );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getIsRequired).collect(Collectors.toList()),List.of(true) );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getLabel).collect(Collectors.toList()),List.of(LocalizedString.ofEnglish("Test-Label")) );
    }


    @Test
    public void getAllProductTypeTest()
    {
        ProductTypePagedQueryResponse queryResponse = ProductTypePagedQueryResponseBuilder.of()
                .results(
                        productType
                )
                .buildUnchecked();

        ByProjectKeyProductTypesRequestBuilder byProjectKeyProductTypesRequestBuilder = Mockito.mock(ByProjectKeyProductTypesRequestBuilder.class);
        Mockito.when(apiRoot.productTypes()).thenReturn(byProjectKeyProductTypesRequestBuilder);

        ByProjectKeyProductTypesGet byProjectKeyProductTypesGet = Mockito.mock(ByProjectKeyProductTypesGet.class);
        Mockito.when(byProjectKeyProductTypesRequestBuilder.get()).thenReturn(byProjectKeyProductTypesGet);
        Mockito.when(byProjectKeyProductTypesGet.withLimit("20")).thenReturn(byProjectKeyProductTypesGet);

        ApiHttpResponse<ProductTypePagedQueryResponse> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductTypesGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(queryResponse);

        ProductTypePagedQueryResponse expectedProductTypes = productTypeService.getAllProductTypes("20");

        Assertions.assertEquals(expectedProductTypes.getResults().stream().map(ProductType::getName).collect(Collectors.toList()),List.of("Test-Name") );
        Assertions.assertEquals(expectedProductTypes.getResults().stream().map(ProductType::getKey).collect(Collectors.toList()),List.of("test-product-type") );
        Assertions.assertEquals(expectedProductTypes.getResults().stream().flatMap(result->result.getAttributes().stream().map(AttributeDefinition::getName)).collect(Collectors.toList()),List.of("Test-Attribute"));
        Assertions.assertEquals(expectedProductTypes.getResults().stream().flatMap(result->result.getAttributes().stream().map(AttributeDefinition::getIsRequired)).collect(Collectors.toList()),List.of(true));
        Assertions.assertEquals(expectedProductTypes.getResults().stream().flatMap(result->result.getAttributes().stream().map(AttributeDefinition::getLabel)).collect(Collectors.toList()),List.of(LocalizedString.ofEnglish("Test-Label")));
    }



    @Test
    public void getProductTypeBYIdTest()
    {
        ByProjectKeyProductTypesRequestBuilder byProjectKeyProductTypesRequestBuilder = Mockito.mock(ByProjectKeyProductTypesRequestBuilder.class);
        Mockito.when(apiRoot.productTypes()).thenReturn(byProjectKeyProductTypesRequestBuilder);

        ByProjectKeyProductTypesByIDRequestBuilder byProjectKeyProductTypesByIDRequestBuilder = Mockito.mock(ByProjectKeyProductTypesByIDRequestBuilder.class);
        Mockito.when(byProjectKeyProductTypesRequestBuilder.withId("12345")).thenReturn(byProjectKeyProductTypesByIDRequestBuilder);

        ByProjectKeyProductTypesByIDGet byProjectKeyProductTypesByIDGet = Mockito.mock(ByProjectKeyProductTypesByIDGet.class);
        Mockito.when(byProjectKeyProductTypesByIDRequestBuilder.get()).thenReturn(byProjectKeyProductTypesByIDGet);

        ApiHttpResponse<ProductType> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductTypesByIDGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(productType);

        ProductType expectedProductType = productTypeService.getProductTypeById(productType.getId());

        Assertions.assertEquals(expectedProductType.getKey(),"test-product-type");
        Assertions.assertEquals(expectedProductType.getName(),"Test-Name");
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getName).collect(Collectors.toList()), List.of("Test-Attribute") );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getIsRequired).collect(Collectors.toList()),List.of(true) );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getLabel).collect(Collectors.toList()),List.of(LocalizedString.ofEnglish("Test-Label")) );
    }



    @Test
    public void deleteProductTypeTest()
    {
        ByProjectKeyProductTypesRequestBuilder byProjectKeyProductTypesRequestBuilder = Mockito.mock(ByProjectKeyProductTypesRequestBuilder.class);
        Mockito.when(apiRoot.productTypes()).thenReturn(byProjectKeyProductTypesRequestBuilder);

        ByProjectKeyProductTypesByIDRequestBuilder byProjectKeyProductTypesByIDRequestBuilder = Mockito.mock(ByProjectKeyProductTypesByIDRequestBuilder.class);
        Mockito.when(byProjectKeyProductTypesRequestBuilder.withId("12345")).thenReturn(byProjectKeyProductTypesByIDRequestBuilder);

        ByProjectKeyProductTypesByIDDelete byProjectKeyProductTypesByIDDelete = Mockito.mock(ByProjectKeyProductTypesByIDDelete.class);
        Mockito.when(byProjectKeyProductTypesByIDRequestBuilder.delete("1")).thenReturn(byProjectKeyProductTypesByIDDelete);

        ApiHttpResponse<ProductType> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyProductTypesByIDDelete.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(productType);

        ProductType expectedProductType = productTypeService.deleteProductTypeById(productType.getId(), "1");

        Assertions.assertEquals(expectedProductType.getKey(),"test-product-type");
        Assertions.assertEquals(expectedProductType.getName(),"Test-Name");
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getName).collect(Collectors.toList()), List.of("Test-Attribute") );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getIsRequired).collect(Collectors.toList()),List.of(true) );
        Assertions.assertEquals(expectedProductType.getAttributes().stream().map(AttributeDefinition::getLabel).collect(Collectors.toList()),List.of(LocalizedString.ofEnglish("Test-Label")) );

    }
}
