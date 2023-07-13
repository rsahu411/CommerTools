package com.CommerceTool.ProductSelection;

import com.commercetools.api.models.product_selection.ProductSelection;
import com.commercetools.api.models.product_selection.ProductSelectionPagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-projections")
public class ProductSelectionController {

    @Autowired
    private ProductSelectionService service;

    // Create Product-Selection
    @PostMapping
    public ProductSelection createProductSelection(@RequestBody ProductSelectionDTO productSelectionDTO)
    {
        return service.createProductSelection(productSelectionDTO);
    }



    // Update Product-Selection
    @PostMapping("/{productSelectionId}")
    public ProductSelection updateProductSelection(@RequestBody ProductSelectionDTO productSelectionDTO,
                                                   @PathVariable String productSelectionId)
    {
        return service.updateProductSelection(productSelectionDTO,productSelectionId);
    }





    // Query Product-Selections
    @GetMapping
    public ProductSelectionPagedQueryResponse getAllProductSelection(@RequestParam(defaultValue = "20",required = false)String limit)
    {
        return service.getAllProductSelection(limit);
    }


    // Get Product-Selection By Id
    @GetMapping("/{productSelectionId}")
    public ProductSelection getProductSelectionById(@PathVariable String productSelectionId)
    {
        return service.getProductSelectionById(productSelectionId);
    }



    // Delete Product-Selection By Id
    @DeleteMapping("/{productSelectionId}")
    public ProductSelection deleteProductSelectionById(@PathVariable String productSelectionId,
                                                       @RequestParam(defaultValue = "1")String version)
    {
        return service.deleteProductSelectionById(productSelectionId,version);
    }
}
