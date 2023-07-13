package com.CommerceTool.Products;

import com.CommerceTool.Product.ProductController;
import com.CommerceTool.Product.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;


}
