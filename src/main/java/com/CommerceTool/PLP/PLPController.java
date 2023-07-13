package com.CommerceTool.PLP;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PLPController {

    @Autowired
    private CategoryCombineService categoryCombineService;
    @Autowired
    private ProjectApiRoot apiRoot;


    // Get
    @GetMapping("/products-plp")
    public ProductProjectionPagedSearchResponse getMenClothingSubcategoriesProduct(@RequestParam(defaultValue = "",required = false)  String category,
                                                                                   @RequestParam(defaultValue ="" ,required = false) String color,
                                                                                   @RequestParam(defaultValue = "",required = false) String size,
                                                                                   @RequestParam(defaultValue = "",required = false) String price)
    {
        int x = 0;
        if (!category.isEmpty())
            x++;
        if (!color.isEmpty())
            x++;
        if (!price.isEmpty())
            x++;
        if (!size.isEmpty())
            x++;


        System.out.println(x);

        switch (x) {
            case 1 -> {
                if (!category.isEmpty())
                    return categoryCombineService.getCategoryFilterProduct(category);
                else if (!color.isEmpty())
                    return categoryCombineService.getColorFilterProduct(color);
                else if (!size.isEmpty())
                    return categoryCombineService.getSizeFilterProduct(size);
                else
                    return categoryCombineService.getPriceFilterProduct(price);
            }
            case 2 -> {
                if (!category.isEmpty() && !color.isEmpty())
                    return categoryCombineService.getCategoryAndColorFilterProduct(category, color);
                else if (!category.isEmpty() && !size.isEmpty())
                    return categoryCombineService.getCategoryAndSizeFilterProduct(category, size);
                else if (!color.isEmpty() && !size.isEmpty())
                    return categoryCombineService.getColorAndSizeFilterProduct(color, size);
                else if (!category.isEmpty())
                    return categoryCombineService.getCategoryAndPriceFilterProduct(category, price);
                else if (!color.isEmpty())
                    return categoryCombineService.getColorAndPriceFilterProduct(color, price);
                else
                    return categoryCombineService.getPriceAndSizeFilterProduct(price, size);
            }
            case 3 -> {
                if (!category.isEmpty() && !color.isEmpty() && !size.isEmpty())
                    return categoryCombineService.getCategoryColorAndSizeFilterProduct(category, color, size);
                else if (!category.isEmpty() && !color.isEmpty())
                    return categoryCombineService.getCategoryColorAndPriceFilterProduct(category, color, price);
                else if (!color.isEmpty())
                    return categoryCombineService.getColorSizeAndPriceFilterProduct(color, size, price);
                else
                    return categoryCombineService.getCategorySizeAndPriceFilterProduct(category, size, price);
            }
            case 4 -> {
                return categoryCombineService.getCategoryColorPriceAndSizeFilterProduct(category, color, price, size);
            }
            default -> {
                return categoryCombineService.getAllProduct();
            }
        }
    }


    @GetMapping("/sort")
    public ProductProjectionPagedSearchResponse getProductsPriceSort(@RequestParam(defaultValue = "price asc",required = false)String sort)
    {

        List<String> stringList = Arrays.asList("Arvind","Mishra","Vaibhav","Rishabh","Jakhda","Vaishali","Poonam","Ayush","Goutom");

        stringList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);



        return apiRoot.productProjections().search().get().addSort(sort).executeBlocking().getBody();
    }
}
