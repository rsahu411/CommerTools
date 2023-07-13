package com.CommerceTool.Order.OrderEdit;

import com.commercetools.api.models.order_edit.OrderEdit;
import com.commercetools.api.models.order_edit.OrderEditPagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/edits")
public class OrderEditController {

    @Autowired
    private OrderEditService service;


    // Create Order-Edit
    @PostMapping
    public OrderEdit createOrderEdit(@RequestBody OrderEditDTO orderEditDTO)
    {
        return service.createOrderEdit(orderEditDTO);
    }


    // Apply Order-Edit
    @PostMapping("/{orderEditId}/apply")
    public OrderEdit appliedOrderEdit(@PathVariable String orderEditId,@RequestBody OrderEditDTO orderEditDTO)
    {
        return service.appliedOrderEdit(orderEditId, orderEditDTO);
    }




    // Get All Order-Edits
    @GetMapping
    public OrderEditPagedQueryResponse getAllOrderEdit(@RequestParam(defaultValue = "100",required = false) String limit)
    {
        return service.getAllOrderEdit(limit);
    }




    // Get Order-Edit By Id
    @GetMapping("/{orderEditId}")
    public OrderEdit GetOrderEditById(@PathVariable String orderEditId)
    {
        return service.GetOrderEditById(orderEditId);
    }




    // Delete Order-Edit
    @DeleteMapping("/{orderEditId}")
    public OrderEdit deleteOrderEditById(@PathVariable String orderEditId, @RequestParam long version)
    {
        return service.deleteOrderEditById(orderEditId,version);
    }

}
