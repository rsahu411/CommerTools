package com.CommerceTool.Customer;

import static org.mockito.ArgumentMatchers.any;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

//@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository repository;


    @Test
    void createCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO
                .builder()
             //   .email("mishra.Shubham@hybris.com")
               // .firstName("Shubham")
                .build();



        Customer customer = CustomerBuilder
                .of()
               // .email("mishra.Shubham@hybris.com")
                .firstName("Rishabh")
//                .middleName("Kumar")
//                .lastName("Mishra")
//                .password("mishraShubham")
//                .customerNumber("1052")
//                .externalId("Mishra123")
//                .companyName("HybrisWorld")
                .buildUnchecked();
        Mockito.when(repository.createCustomer(any())).thenReturn(customer);


//        customerDTO.setMiddleName(customer.getMiddleName());
//        customerDTO.setLastName(customer.getLastName());
//        customerDTO.setCompanyName(customer.getCompanyName());
//        customerDTO.setExternalId(customer.getExternalId());
//        customerDTO.setPassword(customer.getPassword());
//        customerDTO.setCustomerNumber(customer.getCustomerNumber());


        Customer apiCustomer = customerService.createCustomer(customerDTO);
        System.out.println(apiCustomer.getFirstName());
     //   System.out.println(apiCustomer.getFirstName());

        Assertions.assertEquals("Rishabh",apiCustomer.getFirstName());
//      Assertions.assertEquals("Kumar",apiCustomer.getMiddleName());
//        Assertions.assertEquals("Mishra",apiCustomer.getLastName());
//        Assertions.assertEquals("mishraShubham",apiCustomer.getPassword());
//        Assertions.assertEquals("1052",apiCustomer.getCustomerNumber());
//        Assertions.assertEquals("Mishra123",apiCustomer.getExternalId());
//        Assertions.assertEquals("HybrisWorld",apiCustomer.getCompanyName());
    }
}