package com.CommerceTool.Order;

import com.CommerceTool.Cart.CartService;
import com.CommerceTool.Validation.ApiValidation;
import com.CommerceTool.exceptions.GlobalExceptionHandler;
import com.CommerceTool.exceptions.InvalidResourceException;
import com.CommerceTool.exceptions.NullPaymentException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.order.*;
//import jakarta.validation.Valid;
import com.commercetools.api.models.type.FieldContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Validated
@Slf4j
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private CartService cartService;

    @Autowired
    ApiValidation validation;


    @Autowired
    ProjectApiRoot apiRoot;

    // Create Order
    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO)
    {
        Cart cart = apiRoot.carts().withId(orderDTO.getCartId()).get().executeBlocking().getBody();
        List<String> discountCodeStates = cart.getDiscountCodes().stream().map(state -> state.getState().name()).collect(Collectors.toList());
    //    validation.validate(orderDetails,null);

       // String discountCodeId = cart.getDiscountCodes().get(0).getDiscountCode().getId();
       // DiscountCode discountCode = apiRoot.discountCodes().withId(discountCodeId).get().executeBlocking().getBody();

        try {

            if (discountCodeStates.stream().anyMatch(s-> s.equals("NOT_VALID"))) {
              // service.removeDiscountCode(orderDetails, cart.getId());
                throw new InvalidResourceException("Cart",cart.getId(),"gsvqgv");
            }

            else if (cart.getPaymentInfo()==null)
            {
                throw new NullPaymentException("Cart",cart.getId());
            }

            else
                return new ResponseEntity<>(service.createOrder(orderDTO),HttpStatus.CREATED);
        }
        catch (InvalidResourceException exception){
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
             return handler.InvalidResourceExceptionHandler(exception);
        }


    }



    // Get All Orders
    @GetMapping
    public OrderPagedQueryResponse getAllOrders(@RequestParam Long limit)
    {
        return service.gatAllOrder(limit);
    }



    // Get Order By Id
    @GetMapping("/{orderId}")
    public Order gatOrderById(@PathVariable String orderId)
    {
        return service.getOrderById(orderId);
    }



    // Delete Order By Id
    @DeleteMapping("/{orderId}")
    public Order deleteOrderById(@PathVariable String orderId,@RequestParam(defaultValue = "1",required = false) String version)
    {
        return service.deleteOrderById(orderId,version);
    }




    // Add TransitionState
    @PostMapping("/states/{orderId}")
    public Order AddTransitionState(@RequestBody OrderDTO orderDTO, @PathVariable String orderId)
    {
        return service.AddTransitionState(orderDTO,orderId);
    }




   // Add delivery
    @PostMapping("/add-delivery/{orderId}")
    public Order AddDelivery(@RequestBody OrderDTO orderDTO, @PathVariable String orderId)
    {
        return service.addDelivery(orderDTO,orderId);
    }





    // Remove Delivery
    @PostMapping("/remove-delivery/{orderId}")
    public Order removeDelivery(@RequestBody OrderDTO orderDTO, @PathVariable String orderId)
    {
        return service.removeDelivery(orderDTO,orderId);
    }





    // Add Payment
    @PostMapping("/add-payment/{orderId}")
    public Order addPayment(@RequestBody OrderDTO orderDTO, @PathVariable String orderId)
    {
        return service.addPayment(orderDTO,orderId);
    }




    // Remove Payment
    @PostMapping("/remove-payment/{orderId}")
    public Order removePayment(@RequestBody OrderDTO orderDTO, @PathVariable String orderId)
    {
        return service.removePayment(orderDTO,orderId);
    }


    @GetMapping("/test/{orderId}")
    public List<LocalizedString> test(@PathVariable String orderId, @RequestParam String deliveryId)
    {
        return service.test(orderId,deliveryId);
    }

}
