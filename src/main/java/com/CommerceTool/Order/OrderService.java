package com.CommerceTool.Order;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.cart.CartUpdateActionBuilder;
import com.commercetools.api.models.common.BaseAddress;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.discount_code.DiscountCodeReference;
import com.commercetools.api.models.order.*;
import com.commercetools.api.models.payment.PaymentResourceIdentifier;
import com.commercetools.api.models.state.StateResourceIdentifier;
import com.commercetools.api.models.type.FieldContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

   @Autowired
    ProjectApiRoot apiRoot;

   @Autowired
   OrderRepository repository;


    // Create Order
    public Order createOrder(OrderDTO orderDTO)
    {
        OrderFromCartDraft order = OrderFromCartDraft
                .builder()
                .id(orderDTO.getCartId())
                .version(orderDTO.getVersion())
                .orderNumber(orderDTO.getOrderNumber())
                .build();

        return repository.createOrder(order);
    }



    // Get All Order
    public OrderPagedQueryResponse gatAllOrder(Long limit)
    {
        OrderPagedQueryResponse orders = apiRoot
                .orders()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return orders;
    }





    // Get Order By Id
    public Order getOrderById(String orderId)
    {
        Order order = apiRoot
                .orders()
                .withId(orderId)
                .get()
                .executeBlocking()
                .getBody();
        return order;
    }






    // Delete Order By Id
    public Order deleteOrderById(String orderId,String version)
    {
        Order order = apiRoot
                .orders()
                .withId(orderId)
                .delete(version)
                .executeBlocking()
                .getBody();
        return order;
    }




    // Add Transition-State
    public Order AddTransitionState(OrderDTO orderDTO, String orderId)
    {
        Order order = apiRoot.orders().withId(orderId).get().executeBlocking().getBody();
        OrderUpdate orderUpdate = OrderUpdate
                .builder()
                .version(order.getVersion())
                .actions(OrderUpdateAction.transitionStateBuilder()
                        .state(StateResourceIdentifier
                                .builder()
                                .id(orderDTO.getStateId())
                                .build())
                        .build())
                .build();

        Order updatedOrder = apiRoot
                .orders()
                .withId(order.getId())
                .post(orderUpdate)
                .executeBlocking()
                .getBody();

        return  updatedOrder;

    }




    // Add Delivery
    public Order addDelivery(OrderDTO orderDTO, String id)
    {
        Order order = apiRoot.orders().withId(id).get().executeBlocking().getBody();

        List<DeliveryItem> deliveryItems = orderDTO.getItems()
                .stream()
                .map( lineItem -> {
                    return DeliveryItem
                            .builder()
                            .id(lineItem.getId())
                            .quantity(lineItem.getQuantity())
                            .build();
                        }

                )
                .collect(Collectors.toList());

        List<ParcelDraft> parcelDrafts = orderDTO.getParcels()
                .stream()
                .map( parcelDetails -> {
                    return ParcelDraft
                            .builder()
                            .measurements(ParcelMeasurements
                                    .builder()
                                    .lengthInMillimeter(parcelDetails.getLengthInMillimeter())
                                    .widthInMillimeter(parcelDetails.getWidthInMilliMeter())
                                    .heightInMillimeter(parcelDetails.getHeightInMillimeter())
                                    .weightInGram(parcelDetails.getWeightInGram())
                                    .build())
                            .trackingData(TrackingData
                                    .builder()
                                    .trackingId(parcelDetails.getTrackingId())
                                    .carrier(parcelDetails.getCarrier())
                                    .provider(parcelDetails.getProvider())
                                    .providerTransaction(parcelDetails.getProviderTransaction())
                                    .isReturn(parcelDetails.isReturn())
                                    .build())
                         //   .items(deliveryItems)
                            .build();
                        }

                )
                .collect(Collectors.toList());

        OrderUpdate orderUpdate = OrderUpdate
                .builder()
                .version(order.getVersion())
                .actions(OrderUpdateAction
                        .addDeliveryBuilder()
                        .items(deliveryItems)
                        .parcels(parcelDrafts)
                        .address(BaseAddress
                                .builder()
                                .key(orderDTO.getKey())
                                .title(orderDTO.getTitle())
                                .salutation(orderDTO.getSalutation())
                                .firstName(orderDTO.getFirstName())
                                .lastName(orderDTO.getLastName())
                                .streetNumber(orderDTO.getStreetNumber())
                                .streetName(orderDTO.getStreetName())
                                .postalCode(orderDTO.getPostalCode())
                                .region(orderDTO.getRegion())
                                .city(orderDTO.getCity())
                                .state(orderDTO.getState())
                                .country(orderDTO.getCountry())
                                .build()

                        )
                        .build())
                .build();

        Order updatedOrder = apiRoot
                .orders()
                .withId(order.getId())
                .post(orderUpdate)
                .executeBlocking()
                .getBody();

        return  updatedOrder;
    }




    // Remove Delivery
    public Order removeDelivery(OrderDTO orderDTO, String id)
    {
        Order order = apiRoot.orders().withId(id).get().executeBlocking().getBody();

        OrderUpdate orderUpdate = OrderUpdate
                .builder()
                .version(order.getVersion())
                .actions(OrderUpdateAction
                        .removeDeliveryBuilder()
                        .deliveryId(orderDTO.getDeliveryId())
                        .build())
                .build();

        Order updatedOrder = apiRoot
                .orders()
                .withId(order.getId())
                .post(orderUpdate)
                .executeBlocking()
                .getBody();

        return  updatedOrder;

    }




    // Add Payment in Order
    public Order addPayment(OrderDTO orderDTO, String id)
    {
        Order order = apiRoot.orders().withId(id).get().executeBlocking().getBody();

        OrderUpdate orderUpdate = OrderUpdate
                .builder()
                .version(order.getVersion())
                .actions(OrderUpdateAction
                        .addPaymentBuilder()
                        .payment(PaymentResourceIdentifier
                                .builder()
                                .id(orderDTO.getPaymentId())
                                .build()
                        )
                        .build())
                .build();

        Order updatedOrder = apiRoot
                .orders()
                .withId(order.getId())
                .post(orderUpdate)
                .executeBlocking()
                .getBody();

        return  updatedOrder;

    }





    // Remove Payment in Order
    public Order removePayment(OrderDTO orderDTO, String id)
    {
        Order order = apiRoot.orders().withId(id).get().executeBlocking().getBody();

        OrderUpdate orderUpdate = OrderUpdate
                .builder()
                .version(order.getVersion())
                .actions(OrderUpdateAction
                        .removePaymentBuilder()
                        .payment(PaymentResourceIdentifier
                                .builder()
                                .id(orderDTO.getPaymentId())
                                .build()
                        )
                        .build())
                .build();

        Order updatedOrder = apiRoot
                .orders()
                .withId(order.getId())
                .post(orderUpdate)
                .executeBlocking()
                .getBody();

        return  updatedOrder;

    }


    public Order test() {


        System.out.println("Rishabh Sahu");
        return  null;
    }



    public Cart removeDiscountCode(OrderDTO orderDTO, String cartId)
    {
        Cart cart = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();
        String discountCodeId =  cart.getDiscountCodes().get(0).getDiscountCode().getId();

        CartUpdate cartUpdate = CartUpdate
                .builder()
                .version(cart.getVersion())
                .actions(CartUpdateActionBuilder
                        .of()
                        .removeDiscountCodeBuilder()
                        .discountCode(DiscountCodeReference.builder().id(discountCodeId).build())
                        .build())
                .build();

        Cart updatedCart = apiRoot
                .carts()
                .withId(cart.getId())
                .post(cartUpdate)
                .executeBlocking()
                .getBody();
        return updatedCart;
    }




    //task
    public List<LocalizedString> test(String orderId,String deliveryId)
    {
        Order order = apiRoot.orders().withId(orderId).get().executeBlocking().getBody();

       Delivery delivery= order.getShippingInfo().getDeliveries().stream().filter(e->e.getId().equals(deliveryId)).findFirst().orElse(null);
       List<String> itemsId = delivery.getItems().stream().map(item-> item.getId()).collect(Collectors.toList());
       List<LocalizedString> productsName = order.getLineItems().stream().map(e->e.getName()).collect(Collectors.toList());

      //  System.out.println(delivery.getCustom().getFields().values().get("shipmentStatus"));
       // return delivery.getCustom().getFields().values().get("shipmentStatus");
        return productsName;
    }
}
