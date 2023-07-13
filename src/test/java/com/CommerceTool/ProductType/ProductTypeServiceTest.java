package com.CommerceTool.ProductType;

import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product_type.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductTypeServiceTest {

    @InjectMocks
    private ProductTypeService productTypeService;

    @Mock
    private ProductTypeRepository repository;


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
    public void createProductType() throws Exception {

        ProductTypeDTO dto = ProductTypeDTO
                .builder()
                .name("Test-Name")
                .key("test-Key")
                .description("Test-Description")
//                .attributesDetails(List.of(AttributesDTO.builder()
//                              //  .type(AttributeType.textBuilder().build())
//                                .attributeName("Brand")
//                                .isRequired(true)
//                                .isSearchable(true)
//                                .label("Test-Label")
//                                .inputHint(TextInputHint.SINGLE_LINE)
//                                .attributeConstraint(AttributeConstraintEnum.NONE)
//                        .build()))
                .build();
        Mockito.when(repository.createProductType(any(ProductTypeDraft.class))).thenReturn(productType);
        ProductType expectedProductType = productTypeService.createProductType(dto);

        Assertions.assertEquals(expectedProductType.getKey(),"test-product-type");
    }
}
