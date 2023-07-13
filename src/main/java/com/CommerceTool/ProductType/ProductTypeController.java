package com.CommerceTool.ProductType;

import com.CommerceTool.Product.ProductDTO;
import com.commercetools.api.models.product_type.ProductType;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-types")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    // Create Product Type
    @PostMapping
    public ResponseEntity<ProductType> createProductType(@RequestBody ProductTypeDTO productTypeDTO)
    {
        return new ResponseEntity<>(productTypeService.createProductType(productTypeDTO), HttpStatus.CREATED);
    }



    // Get All Product Type
    @GetMapping
    public ResponseEntity<ProductTypePagedQueryResponse> getAllProductTypes(@RequestParam String Limit)
    {
        return new ResponseEntity<>(productTypeService.getAllProductTypes(Limit),HttpStatus.OK);
    }



    // Get ProductType By id
    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getProductTypeBYId(@PathVariable String id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productTypeService.getProductTypeById(id));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ProductType> deleteProductTypeById(@PathVariable String id,
                                                             @RequestParam(defaultValue = "1",required = false)String version)
    {
        return new ResponseEntity<>(productTypeService.deleteProductTypeById(id,version),HttpStatus.OK);
    }



    // Update Product Type
    @PostMapping("/{id}")
    public ProductType updateProductType(@PathVariable String id,@RequestBody ProductTypeDTO productTypeDTO)
    {
        return productTypeService.productTypeUpdate(id, productTypeDTO);
    }
}
