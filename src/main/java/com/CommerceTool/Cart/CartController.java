package com.CommerceTool.Cart;

import com.CommerceTool.Validation.ApiValidation;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartPagedQueryResponse;
import com.commercetools.api.models.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService service;

    //create cart
    @PostMapping
    public Cart createCart(@RequestBody CartDTO cartDTO)
    {
       return service.createCart(cartDTO);
    }

    @GetMapping
    public CartPagedQueryResponse getAllCarts(@RequestParam String Limit)
    {
        return service.getAllCarts(Limit);
    }



    @GetMapping("/{Id}")
    public Product getCartById(@PathVariable String Id)
    {
        return service.getCartById(Id);
    }

    @GetMapping("/quantity/{id}")
    public long getQuantity(@PathVariable String id)
    {
        return service.getQuantity(id);
    }

    @GetMapping("/customer-id={customerId}")
    public Cart gtCartByCustomerId(@PathVariable String customerId)
    {
        return service.GetCartByCustomerId(customerId);
    }


    @DeleteMapping("/{Id}")
    public Cart deleteCartById(@PathVariable String Id,@RequestParam(required = false, defaultValue="1L") long version)
    {
        return service.deleteCartById(Id,version);
    }


    @PostMapping("/addShippingAddress/{id}")
    public  Cart addShippingAddress(@RequestBody CartDTO cartDTO, @PathVariable String id)
    {
        return service.addShippingAddress(cartDTO,id);
    }


    @PostMapping("/addLineItem/{id}")
    public  Cart addLineItem(@RequestBody CartDTO cartDTO, @PathVariable String id)
    {
        return service.addLineItem(cartDTO,id);
    }


    @GetMapping("/orders/{Id}")
    public Product getProductByOrderId(@PathVariable String Id)
    {
        return service.getProductByOrderId(Id);
    }
}
