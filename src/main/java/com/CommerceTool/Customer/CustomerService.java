package com.CommerceTool.Customer;

import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.BaseAddress;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupDraft;
import com.commercetools.api.models.customer_group.CustomerGroupPagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    ProjectApiRoot apiRoot;
    @Autowired
    CustomerRepository customerRepository;


    // Create a new Customer
    public Customer createCustomer(CustomerDTO customerDTO)
    {
        CustomerDraft customerDraft=CustomerDraft
                .builder()
                .firstName(customerDTO.getFirstName())
                .middleName(customerDTO.getMiddleName())
                .lastName(customerDTO.getLastName())
                .password(customerDTO.getPassword())
                .email(customerDTO.getEmail())
                .customerNumber(customerDTO.getCustomerNumber())
                .externalId(customerDTO.getExternalId())
                .companyName(customerDTO.getCompanyName())
                .build();
        return customerRepository.createCustomer(customerDraft);
    }




    // Get All Customers
    public CustomerPagedQueryResponse getAllCustomer(String limit)
    {

        return apiRoot
                .customers()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
    }




    // Get Customer By Id
    public Customer getCustomerById(String id)
    {
        return apiRoot
                .customers()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
    }





    // Customer Update
    public Customer updateCustomer(String id, CustomerDTO customerDTO)
    {
          Customer customer = getCustomerById(id);
        CustomerUpdate customerUpdate = CustomerUpdate
                .builder()
                .version(customer.getVersion())
                .plusActions(actionBuilder -> actionBuilder
                        .addAddressBuilder()
                        .address(BaseAddress
                                .builder()
                                .streetNumber(customerDTO.getStreetNumber())
                                .streetName(customerDTO.getStreetName())
                                .building(customerDTO.getBuilding())
                                .apartment(customerDTO.getApartment())
                                .pOBox(customerDTO.getPOBox())
                                .postalCode(customerDTO.getPostalCode())
                                .city(customerDTO.getCity())
                                .state(customerDTO.getState())
                                .country(customerDTO.getCountry())
                                .region(customerDTO.getRegion())
                                .build()))
                .build();

        return apiRoot.customers().withId(customer.getId()).post(customerUpdate).executeBlocking().getBody();
    }




    public Customer updateAction(CustomerDTO customerDTO, String customerId)
    {
        List<CustomerUpdateAction> updateAction = customerDTO.getActions().stream()
                .map(actionDTO ->
                {
                    return switch(actionDTO.getAction())
                            {
                                case "addAddress" -> CustomerUpdateAction
                                        .addAddressBuilder()
                                        .address(baseAddress(actionDTO))
                                        .build();
                                case "addBillingAddressId" -> CustomerUpdateAction
                                        .addBillingAddressIdBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .build();
                                case "addShippingAddressId" -> CustomerUpdateAction
                                        .addShippingAddressIdBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .build();
                                case "changeAddress" -> CustomerUpdateAction
                                        .changeAddressBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .address(baseAddress(actionDTO))
                                        .build();
                                case "changeEmail" -> CustomerUpdateAction
                                        .changeEmailBuilder()
                                        .email(actionDTO.getEmail())
                                        .build();
                                case "removeAddress" -> CustomerUpdateAction
                                        .removeAddressBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .build();
                                case "removeBillingAddressId" -> CustomerUpdateAction
                                        .removeBillingAddressIdBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .build();
                                case "removeShippingAddressId" -> CustomerUpdateAction
                                        .removeShippingAddressIdBuilder()
                                        .addressId(actionDTO.getAddressId())
                                        .build();
                                case "setFirstName" -> CustomerUpdateAction
                                        .setFirstNameBuilder()
                                        .firstName(actionDTO.getFirstName())
                                        .build();
                                case "setMiddleName" -> CustomerUpdateAction
                                        .setMiddleNameBuilder()
                                        .middleName(actionDTO.getMiddleName())
                                        .build();
                                case "setLastName" -> CustomerUpdateAction
                                        .setLastNameBuilder()
                                        .lastName(actionDTO.getLastName())
                                        .build();
                                case "setKey" -> CustomerUpdateAction
                                        .setKeyBuilder()
                                        .key(actionDTO.getKey())
                                        .build();
                                case "setSalutation" -> CustomerUpdateAction
                                        .setSalutationBuilder()
                                        .salutation(actionDTO.getSalutation())
                                        .build();
                                case "setTitle" -> CustomerUpdateAction
                                        .setTitleBuilder()
                                        .title(actionDTO.getTitle())
                                        .build();
                                case "setExternalId" -> CustomerUpdateAction
                                        .setExternalIdBuilder()
                                        .externalId(actionDTO.getExternalId())
                                        .build();
                                case "setDateOfBirth" -> CustomerUpdateAction
                                        .setDateOfBirthBuilder()
                                        .dateOfBirth(actionDTO.getDateOfBirth())
                                        .build();
                                case "setCustomerNumber" -> CustomerUpdateAction
                                        .setCustomerNumberBuilder()
                                        .customerNumber(actionDTO.getCustomerNumber())
                                        .build();
                                default -> throw new InvalidActionException(actionDTO.getAction());
                            };
                })
                .collect(Collectors.toList());


        Customer customer = apiRoot.customers().withId(customerId).get().executeBlocking().getBody();

        CustomerUpdate customerUpdate = CustomerUpdate
                .builder()
                .version(customer.getVersion())
                .actions(updateAction)
                .build();

        return apiRoot.customers().withId(customer.getId()).post(customerUpdate).executeBlocking().getBody();
    }



    public BaseAddress baseAddress(ActionDTO actionDTO)
    {
        return BaseAddress
                .builder()
                .key(actionDTO.getKey())
                .title(actionDTO.getTitle())
                .salutation(actionDTO.getSalutation())
                .firstName(actionDTO.getFirstName())
                .lastName(actionDTO.getLastName())
                .streetName(actionDTO.getStreetName())
                .streetNumber(actionDTO.getStreetNumber())
                .postalCode(actionDTO.getPostalCode())
                .city(actionDTO.getCity())
                .region(actionDTO.getRegion())
                .state(actionDTO.getState())
                .country(actionDTO.getCountry())
                .build();
    }



    // Delete Customer By Id
    public Customer deleteCustomerById(String id,long version)
    {
        return apiRoot
                .customers()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
    }





    // Get Customer Token
    public CustomerToken getCustomerToken(CustomerDTO customerDTO)
    {
        CustomerCreatePasswordResetToken customerCreatePasswordResetToken = CustomerCreatePasswordResetToken
                .builder().email(customerDTO.getEmail()).build();
        return customerRepository.getCustomerToken(customerCreatePasswordResetToken);
    }





    // Reset Password
    public  Customer resetPassword(CustomerDTO customerDTO)
    {
        CustomerResetPassword customerResetPassword= CustomerResetPassword
                .builder()
                .tokenValue(customerDTO.getTokenValue())
                .newPassword(customerDTO.getNewPassword())
                .build();
        return customerRepository.resetPassword(customerResetPassword);
    }






    // Change Password
    public  Customer changePassword(CustomerDTO customerDTO)
    {
        CustomerChangePassword changePassword = CustomerChangePassword
                .builder()
                .id(customerDTO.getId())
                .version(customerDTO.getVersion())
                .currentPassword(customerDTO.getCurrentPassword())
                .newPassword(customerDTO.getNewPassword())
                .build();
        return customerRepository.changePassword(changePassword);
    }






    // Customer SignIn
    public  CustomerSignInResult getustomerByLogIn(CustomerDTO customerDTO)
    {
        CustomerSignin customer;
        if(customerDTO.getAnonymousId()!= null) {
            customer = CustomerSignin
                    .builder()
                    .email(customerDTO.getEmail())
                    .password(customerDTO.getPassword())
                    .anonymousId(customerDTO.getAnonymousId())
                    .anonymousCartSignInMode(AnonymousCartSignInMode.MERGE_WITH_EXISTING_CUSTOMER_CART)
                    .build();

        }
        else
        {
             customer = CustomerSignin
                    .builder()
                    .email(customerDTO.getEmail())
                    .password(customerDTO.getPassword())
                    .build();
        }
        return customerRepository.getCustomerByLogin(customer);
    }





    // Create Customer-Group
    public CustomerGroup createCustomerGroup(CustomerDTO customerDTO) {

        CustomerGroupDraft customerGroupDraft = CustomerGroupDraft
                .builder()
                .groupName(customerDTO.getCustomerGroupName())
                .key(customerDTO.getCustomerGroupKey())
                .build();
        return customerRepository.createCustomerGroup(customerGroupDraft);
    }



    // Get All Customer Group
    public CustomerGroupPagedQueryResponse getCustomerGroupCustomer() {

        return apiRoot
                .customerGroups()
                .get()
                .executeBlocking()
                .getBody();
    }



    // Get Customer-Group By id
    public CustomerGroup getCustomerGroupById(String id) {

        return apiRoot
                .customerGroups()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
    }



    // Delete Customer-Group By id
    public CustomerGroup deleteCustomerGroupById(String id, long version) {

        return apiRoot
                .customerGroups()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
    }


    public CustomerToken customerEmailVerificationToken(CustomerDTO customerDTO)
    {
        return apiRoot
                .customers()
                .emailToken()
                .post(
                        CustomerCreateEmailToken.builder()
                                .id(customerDTO.getId())
                                .ttlMinutes(customerDTO.getTtlMinutes())
                                .build()
                )
                .executeBlocking()
                .getBody();
    }


    public Customer verifyCustomerEmail(CustomerDTO customerDTO)
    {
        return apiRoot
                .customers()
                .emailConfirm()
                .post(CustomerEmailVerifyBuilder.of().tokenValue(customerDTO.getTokenValue()).build())
                .executeBlocking()
                .getBody();
    }

    public Customer verifyCustomerEmailGet(String  token)
    {
        return apiRoot
                .customers()
                .emailConfirm()
                .post(CustomerEmailVerifyBuilder.of().tokenValue(token).build())
                .executeBlocking()
                .getBody();
    }
}
