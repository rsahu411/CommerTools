package com.CommerceTool.Validation;

import com.CommerceTool.Order.OrderDTO;
import com.CommerceTool.Order.OrderService;
import com.CommerceTool.exceptions.InvalidResourceException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ApiValidation implements Validator {


    @Autowired
    ProjectApiRoot apiRoot;

    @Autowired
    OrderService orderService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        OrderDTO orderDTO = (OrderDTO)target;
        Cart cart = apiRoot.carts().withId(orderDTO.getCartId()).get().executeBlocking().getBody();
        List<String> discountCodeStates = cart.getDiscountCodes().stream().map(state -> state.getState().name()).collect(Collectors.toList());

       // String discountCodeId = cart.getDiscountCodes().get(0).getDiscountCode().getId();
       // DiscountCode discountCode = apiRoot.discountCodes().withId(discountCodeId).get().executeBlocking().getBody();
     //   try {
            if (discountCodeStates.stream().anyMatch(s-> s.equals("NOT_VALID"))) {
                orderService.removeDiscountCode(orderDTO, cart.getId());
                throw new InvalidResourceException("Cart",cart.getId(),"yeset15");
            }
     //   }
//        catch (InvalidResourceException exception){
//            GlobalExceptionHandler handler = new GlobalExceptionHandler();
//             // handler.InvalidResourceExceptionHandler(exception);
//        }

    }


    }
