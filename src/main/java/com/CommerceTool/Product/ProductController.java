package com.CommerceTool.Product;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponse;
import com.commercetools.api.models.product_type.ProductType;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import io.vrap.rmf.base.client.ApiHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProjectApiRoot projectApiRoot;
    @Autowired
    ProductService service;





    // Create Product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO)
    {
        return new ResponseEntity<>(service.createProduct(productDTO),HttpStatus.OK);
    }



    // Get All Product
    @GetMapping
    public CompletableFuture<ApiHttpResponse<ProductPagedQueryResponse>> getProducts(@RequestParam (required = false, defaultValue="5") String limit)
    {
        return service.getProducts(limit);
    }



    // Get Product by id
    @GetMapping("/{Id}")
    public Product getProductById(@PathVariable String Id)
    {
        return service.getProductById(Id);
    }



    @GetMapping("/price/{id}")
    public ResponseEntity<Long> getPrice(@PathVariable String id)
    {
        return new ResponseEntity<>(service.getPrice(id),HttpStatus.OK);
    }

//    // Update Product
//    @PostMapping("/{Id}")
//    public ResponseEntity<Product> updateProducts(@PathVariable String Id, @RequestBody ProductDTO productDTO)
//    {
//        return new ResponseEntity<>(service.updateProducts(Id, productDTO),HttpStatus.OK);
//    }



    // Delete Products
    @DeleteMapping("/{Id}")
    public ResponseEntity<Product> deleteProducts(@PathVariable String Id,@RequestParam(required = false, defaultValue="1L") long version)
    {
        return new ResponseEntity<>(service.deleteProducts(Id,version),HttpStatus.OK);
    }



    @GetMapping("/products")
    ProductPagedQueryResponse productlimit(@RequestParam(required = false, defaultValue="5") int limit)
    {
        return projectApiRoot.products().get().addLimit(limit).executeBlocking().getBody();
    }



    @GetMapping("/products-key")
    ProductPagedQueryResponse productFilter(@RequestParam String slug)
    {
        return projectApiRoot.products().get().addWhere(slug).executeBlocking().getBody();
    }





    @GetMapping("/productProjection")
    ProductProjectionPagedSearchResponse productProjectionFilter(@RequestParam List<String> where)
    {
        return projectApiRoot.productProjections().search().get().addFilter(where).executeBlocking().getBody();
    }


    @GetMapping("/attributesValue/{id}")
    public Object getAttributeValue(@PathVariable String id)
    {
        return service.getAttributeValue(id);
    }

}
