package com.CommerceTool.Customer;

import com.CommerceTool.exceptions.InvalidResourceException;
import com.CommerceTool.exceptions.InvalidResourceIDException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupPagedQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    ProjectApiRoot projectApiRoot;

    @Autowired
    CustomerService service;


    ObjectMapper mapper = new ObjectMapper();

    // Create Customer

    @PostMapping
    public Customer createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = service.createCustomer(customerDTO);
      return customer;

      //  return new ResponseEntity(customer,HttpStatus.CREATED);
    }


//    @PostMapping
//    public ResponseEntity createCustomer(@RequestBody CustomerDTO customerdetails) throws JsonProcessingException {
//        Customer customer = service.createCustomer(customerdetails);
//        mapper.registerModule(new JavaTimeModule());
//       String jsonData= mapper.writeValueAsString(customer);
//
//        System.out.println(jsonData);
//      return new ResponseEntity(customer,HttpStatus.CREATED);
//    }



    //Get all Customer
    @GetMapping
    public ResponseEntity<CustomerPagedQueryResponse>  getAllCustomer( @RequestParam(defaultValue = "50",required = false) String Limit) {

        return new ResponseEntity<>(service.getAllCustomer(Limit),HttpStatus.OK);
    }


    // Download Csv file
    @GetMapping("/csv")
    public void  downloadCsvOfAllCustomer( HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=\"customers.csv\"");

        PrintWriter writer = response.getWriter();
        CustomerPagedQueryResponse customerPagedQueryResponse = projectApiRoot.customers().get().executeBlocking().getBody();

        writer.println("Id,firstName,middleName,lastName,email,customerNumber,externalId");
        for(Customer customer : customerPagedQueryResponse.getResults())
        {
            writer.println(customer.getId()+","+customer.getFirstName()+", "+","+customer.getMiddleName() +customer.getLastName()+
                    ", "+customer.getEmail()+", "+customer.getCustomerNumber()+","+customer.getExternalId());
        }
        writer.flush();
        writer.close();
    }



   // Get Customer By id
    @GetMapping("/{Id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String Id)
    {

        return new ResponseEntity<>(service.getCustomerById(Id),HttpStatus.OK);
    }



    //Delete Customer By id
    @DeleteMapping("/{Id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable String Id,@RequestParam(required = false,defaultValue = "1L") long version)
    {
        return new ResponseEntity<>(service.deleteCustomerById(Id,version),HttpStatus.OK);
    }




    // Update Customer By id
    @PostMapping("/{Id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String Id,@RequestBody CustomerDTO customerDTO)
    {

       return new ResponseEntity<>(service.updateCustomer(Id, customerDTO),HttpStatus.OK);
    }




    // Create Customer Group
    @PostMapping("/groups")
    public ResponseEntity<CustomerGroup> createCustomerGroup(@RequestBody CustomerDTO customerDTO)
    {
        return new ResponseEntity<>(service.createCustomerGroup(customerDTO),HttpStatus.CREATED);
    }



    // Get All Customer-Groups
    @GetMapping("/groups")
    public ResponseEntity<CustomerGroupPagedQueryResponse> getCustomerGroupCustomer()
    {
        return new ResponseEntity<>(service.getCustomerGroupCustomer(),HttpStatus.OK);
    }




    // Get Customer-Group By id
    @GetMapping("/groups/{id}")
    public ResponseEntity<CustomerGroup> getCustomerGroupById(@PathVariable String id)
    {
        return new ResponseEntity<>(service.getCustomerGroupById(id),HttpStatus.OK);
    }




    // Delete Customer-Group By id
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<CustomerGroup> deleteCustomerGroupById(@PathVariable String id,@RequestParam(required = false,defaultValue = "1L") long version)
    {
        return new ResponseEntity<>(service.deleteCustomerGroupById(id,version),HttpStatus.OK);
    }





    // Get Token Value
   @PostMapping("/password-token")
    public CustomerToken getTokenValue(@RequestBody CustomerDTO customerDTO)
   {
       return service.getCustomerToken(customerDTO);
   }




   // Reset Password
   @PostMapping("/password/reset")
    public Customer resetPassword(@RequestBody CustomerDTO customerDTO)
    {
        return service.resetPassword(customerDTO);
    }




    // Change Customer Password
    @PostMapping("/password/change")
    public  Customer changePassword(@RequestBody CustomerDTO customerDTO)
    {
        return service.changePassword(customerDTO);
    }




    // Customer SignIn Result
    @GetMapping("/login")
    public  CustomerSignInResult customerByLogIn(@RequestBody CustomerDTO customerDTO)
    {
       return  service.getustomerByLogIn(customerDTO);
    }


    @PostMapping("/email-token")
    public CustomerToken customerEmailVerificationToken(@RequestBody CustomerDTO customerDTO)
    {
        try {
            return service.customerEmailVerificationToken(customerDTO);
        }catch (Exception e)
        {
            throw new InvalidResourceIDException("Customer", customerDTO.getId());
        }

    }


    @PostMapping("/email/confirm")
    public Customer verifyCustomerEmail(@RequestBody CustomerDTO customerDTO)
    {
        try {
            return service.verifyCustomerEmail(customerDTO);
        }catch (Exception e)
        {
            throw new InvalidResourceIDException("Token","value");
        }

    }


    @GetMapping("/email/confirm/{token}")
    public Customer verifyCustomerEmailGet(@PathVariable String token)
    {
        try {
            return service.verifyCustomerEmailGet(token);
        }catch (Exception e)
        {
            throw new InvalidResourceIDException("Token","value");
        }

    }

}
