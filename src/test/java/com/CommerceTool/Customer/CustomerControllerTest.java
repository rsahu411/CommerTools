package com.CommerceTool.Customer;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {


    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    public void testCreateCustomer(){

        Customer customer = CustomerBuilder
                .of()
                .email("Rishabh@gmail.com")
                .firstName("Rishabh")
                .buildUnchecked();
        Mockito.when(customerService.createCustomer(any())).thenReturn(customer);

        CustomerDTO customerDTO = new  CustomerDTO();
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setFirstName(customer.getFirstName());

        Customer customer1 = customerController.createCustomer(customerDTO);

        Assertions.assertEquals("Rishabh@gmail.com",customer1.getEmail());
        Assertions.assertEquals("Rishabh", customer1.getFirstName());
    }





}

