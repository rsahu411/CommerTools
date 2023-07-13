package com.CommerceTool.Customer;


import static org.mockito.ArgumentMatchers.any;
import com.commercetools.api.client.*;
import com.commercetools.api.models.customer.*;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {
    @InjectMocks
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ProjectApiRoot apiRoot;

    @Test
    public void createCustomerRepositoryTest()
    {
        Customer customer = CustomerBuilder
                .of()
                .email("Rishabh@gmail.com")
                .firstName("Rishabh")
                .buildUnchecked();

        ByProjectKeyCustomersRequestBuilder byProjectKeyCustomersRequestBuilder = Mockito.mock(ByProjectKeyCustomersRequestBuilder.class);
        Mockito.when(apiRoot.customers()).thenReturn(byProjectKeyCustomersRequestBuilder);

        ByProjectKeyCustomersPost byProjectKeyCustomersPost = Mockito.mock(ByProjectKeyCustomersPost.class);
        Mockito.when(byProjectKeyCustomersRequestBuilder.post(any(CustomerDraft.class))).thenReturn(byProjectKeyCustomersPost);

        ApiHttpResponse<CustomerSignInResult> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyCustomersPost.executeBlocking()).thenReturn(apiHttpResponse);

        CustomerSignInResult customerSignInResult = Mockito.mock(CustomerSignInResult.class);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(customerSignInResult);
        Mockito.when(customerSignInResult.getCustomer()).thenReturn(customer);

        CustomerDraft customerDraft = new CustomerDraftImpl();
        customerDraft.setEmail(customer.getEmail());

        Customer actualCustomer = repository.createCustomer(customerDraft);
        Assertions.assertEquals("Rishabh@gmail.com",actualCustomer.getEmail());
    }
    @Test
    public void getAllCustomer()
    {
        CustomerPagedQueryResponse customerPagedQueryResponses = CustomerPagedQueryResponse
                .builder()
                .results(
                        CustomerBuilder.of().email("Rishabh@gmail.com").firstName("Rishabh").buildUnchecked(),
                        CustomerBuilder.of().email("Mishra@gmail.com").firstName("Mishra").buildUnchecked()

                )
                .buildUnchecked();

        String limit = "10";
        ByProjectKeyCustomersRequestBuilder byProjectKeyCustomersRequestBuilder = Mockito.mock(ByProjectKeyCustomersRequestBuilder.class);
        Mockito.when(apiRoot.customers()).thenReturn(byProjectKeyCustomersRequestBuilder);

        ByProjectKeyCustomersGet byProjectKeyCustomersGet = Mockito.mock(ByProjectKeyCustomersGet.class);
        Mockito.when(byProjectKeyCustomersRequestBuilder.get()).thenReturn(byProjectKeyCustomersGet);

        Mockito.when(byProjectKeyCustomersGet.withLimit(limit)).thenReturn(byProjectKeyCustomersGet);

        ApiHttpResponse<CustomerPagedQueryResponse> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyCustomersGet.executeBlocking()).thenReturn(apiHttpResponse);

        Mockito.when(apiHttpResponse.getBody()).thenReturn(customerPagedQueryResponses);

        CustomerPagedQueryResponse queryResponse = customerService.getAllCustomer(limit);

        Assertions.assertEquals(customerPagedQueryResponses, queryResponse);
    }
    @Test
    public void getCustomerByIdTest()
    {
        Customer customer = CustomerBuilder
                .of()
                .id("10")
                .email("Rishabh@gmail.com")
                .firstName("Rishabh")
                .buildUnchecked();

        ByProjectKeyCustomersRequestBuilder byProjectKeyCustomersRequestBuilder = Mockito.mock(ByProjectKeyCustomersRequestBuilder.class);
        Mockito.when(apiRoot.customers()).thenReturn(byProjectKeyCustomersRequestBuilder);

        ByProjectKeyCustomersByIDRequestBuilder byProjectKeyCustomersByIDRequestBuilder = Mockito.mock(ByProjectKeyCustomersByIDRequestBuilder.class);
        Mockito.when(byProjectKeyCustomersRequestBuilder.withId(customer.getId())).thenReturn(byProjectKeyCustomersByIDRequestBuilder);

        ByProjectKeyCustomersByIDGet byProjectKeyCustomersByIDGet = Mockito.mock(ByProjectKeyCustomersByIDGet.class);
        Mockito.when(byProjectKeyCustomersByIDRequestBuilder.get()).thenReturn(byProjectKeyCustomersByIDGet);

        ApiHttpResponse<Customer> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyCustomersByIDGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(customer);

        Customer actualCustomer = customerService.getCustomerById("10");

        Assertions.assertEquals(customer, actualCustomer);
    }
    @Test
    public void deleteCustomerByIdTest()
    {
        Customer customer = CustomerBuilder
                .of()
                .id("10")
                .email("Rishabh@gmail.com")
                .firstName("Rishabh")
                .buildUnchecked();

        ByProjectKeyCustomersRequestBuilder byProjectKeyCustomersRequestBuilder = Mockito.mock(ByProjectKeyCustomersRequestBuilder.class);
        Mockito.when(apiRoot.customers()).thenReturn(byProjectKeyCustomersRequestBuilder);

        ByProjectKeyCustomersByIDRequestBuilder byProjectKeyCustomersByIDRequestBuilder = Mockito.mock(ByProjectKeyCustomersByIDRequestBuilder.class);
        Mockito.when(byProjectKeyCustomersRequestBuilder.withId(customer.getId())).thenReturn(byProjectKeyCustomersByIDRequestBuilder);

        ByProjectKeyCustomersByIDDelete byProjectKeyCustomersByIDDelete = Mockito.mock(ByProjectKeyCustomersByIDDelete.class);
        Mockito.when(byProjectKeyCustomersByIDRequestBuilder.delete(1L)).thenReturn(byProjectKeyCustomersByIDDelete);

        ApiHttpResponse<Customer> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyCustomersByIDDelete.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(customer);

        Customer actualCustomer = customerService.deleteCustomerById("10",1L);
        System.out.println(actualCustomer.getFirstName());

        Assertions.assertEquals(customer,actualCustomer);
    }
}
