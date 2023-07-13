package com.CommerceTool.Reviews;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.commercetools.api.client.*;
import com.commercetools.api.models.common.Reference;
import com.commercetools.api.models.common.ReferenceBuilder;
import com.commercetools.api.models.customer.CustomerReferenceBuilder;
import com.commercetools.api.models.product.ProductResourceIdentifierBuilder;
import com.commercetools.api.models.review.*;
import com.commercetools.api.models.state.StateReferenceBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

    @InjectMocks
    private ReviewService reviewService;

    @InjectMocks
    private ReviewRepository reviewRepository;

    @Mock
    private ProjectApiRoot apiRoot;

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();



    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewRepository).build();


    }

    Review review;
    @BeforeEach
    public void dataSetUp()
    {
       review = ReviewBuilder.of()
               .id("1234567890")
               .version(1L)
               .key("test-review")
               .customer(CustomerReferenceBuilder.of()
                       .id("1234567890")
                       .buildUnchecked())
               .authorName("Test-AuthorName")
               .title("Test-Title")
               .text("Test-Text")
               .rating(3)
               .target(ReferenceBuilder.of().productBuilder().id("12345").buildUnchecked())
               .state(StateReferenceBuilder.of().id("12345").buildUnchecked())
               .buildUnchecked();
    }
    @Test
    public void createReviewTest()
    {
        ByProjectKeyReviewsRequestBuilder byProjectKeyReviewsRequestBuilder = Mockito.mock(ByProjectKeyReviewsRequestBuilder.class);
        Mockito.when(apiRoot.reviews()).thenReturn(byProjectKeyReviewsRequestBuilder);

        ByProjectKeyReviewsPost byProjectKeyReviewsPost = Mockito.mock(ByProjectKeyReviewsPost.class);
        Mockito.when(byProjectKeyReviewsRequestBuilder.post(any(ReviewDraft.class))).thenReturn(byProjectKeyReviewsPost);

        ApiHttpResponse<Review> reviewApiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyReviewsPost.executeBlocking()).thenReturn(reviewApiHttpResponse);
        Mockito.when(reviewApiHttpResponse.getBody()).thenReturn(review);

        Review expectedReview = reviewRepository.createReview(ReviewDraft.builder().buildUnchecked());

        Assertions.assertEquals(expectedReview.getKey(),"test-review");
        Assertions.assertEquals(expectedReview.getCustomer().getId(),"1234567890");
        Assertions.assertEquals(expectedReview.getAuthorName(),"Test-AuthorName");
        Assertions.assertEquals(expectedReview.getTitle(),"Test-Title");
        Assertions.assertEquals(expectedReview.getText(),"Test-Text");
        Assertions.assertEquals(expectedReview.getRating(),3);
        Assertions.assertEquals(expectedReview.getTarget().getId(),"12345");
        Assertions.assertEquals(expectedReview.getState().getId(),"12345");

    }



    @Test
    public void getAllReviewTest()
    {
        ReviewPagedQueryResponse queryResponse = ReviewPagedQueryResponseBuilder.of()
                .results(
                        review
                )
                .buildUnchecked();

        ByProjectKeyReviewsRequestBuilder byProjectKeyReviewsRequestBuilder = Mockito.mock(ByProjectKeyReviewsRequestBuilder.class);
        Mockito.when(apiRoot.reviews()).thenReturn(byProjectKeyReviewsRequestBuilder);

        ByProjectKeyReviewsGet byProjectKeyReviewsGet = Mockito.mock(ByProjectKeyReviewsGet.class);
        Mockito.when(byProjectKeyReviewsRequestBuilder.get()).thenReturn(byProjectKeyReviewsGet);
        Mockito.when(byProjectKeyReviewsGet.withLimit("10")).thenReturn(byProjectKeyReviewsGet);

        ApiHttpResponse<ReviewPagedQueryResponse> apiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyReviewsGet.executeBlocking()).thenReturn(apiHttpResponse);
        Mockito.when(apiHttpResponse.getBody()).thenReturn(queryResponse);

        ReviewPagedQueryResponse expectedReviews = reviewService.GetAllReview("10");

        Assertions.assertEquals(expectedReviews.getResults().stream().map(Review::getKey).collect(Collectors.toList()), List.of("test-review"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(Review::getAuthorName).collect(Collectors.toList()), List.of("Test-AuthorName"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(Review::getTitle).collect(Collectors.toList()), List.of("Test-Title"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(Review::getText).collect(Collectors.toList()), List.of("Test-Text"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(Review::getRating).collect(Collectors.toList()), List.of(3));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(result->result.getCustomer().getId()).collect(Collectors.toList()),List.of("1234567890"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(result->result.getState().getId()).collect(Collectors.toList()), List.of("12345"));
        Assertions.assertEquals(expectedReviews.getResults().stream().map(result->result.getTarget().getId()).collect(Collectors.toList()), List.of("12345"));
    }


    @Test
    public void getReviewByIdTest()
    {
        ByProjectKeyReviewsRequestBuilder byProjectKeyReviewsRequestBuilder = Mockito.mock(ByProjectKeyReviewsRequestBuilder.class);
        Mockito.when(apiRoot.reviews()).thenReturn(byProjectKeyReviewsRequestBuilder);

        ByProjectKeyReviewsByIDRequestBuilder byProjectKeyReviewsByIDRequestBuilder = Mockito.mock(ByProjectKeyReviewsByIDRequestBuilder.class);
        Mockito.when(byProjectKeyReviewsRequestBuilder.withId("1234567890")).thenReturn(byProjectKeyReviewsByIDRequestBuilder);

        ByProjectKeyReviewsByIDGet byProjectKeyReviewsByIDGet = Mockito.mock(ByProjectKeyReviewsByIDGet.class);
        Mockito.when(byProjectKeyReviewsByIDRequestBuilder.get()).thenReturn(byProjectKeyReviewsByIDGet);

        ApiHttpResponse<Review> reviewApiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyReviewsByIDGet.executeBlocking()).thenReturn(reviewApiHttpResponse);
        Mockito.when(reviewApiHttpResponse.getBody()).thenReturn(review);

        Review expectedReview = reviewService.getReviewById(review.getId());

        Assertions.assertEquals(expectedReview.getKey(),"test-review");
        Assertions.assertEquals(expectedReview.getText(),"Test-Text");
        Assertions.assertEquals(expectedReview.getTitle(),"Test-Title");
        Assertions.assertEquals(expectedReview.getRating(),3);
        Assertions.assertEquals(expectedReview.getCustomer().getId(),"1234567890");
        Assertions.assertEquals(expectedReview.getTarget().getId(),"12345");
        Assertions.assertEquals(expectedReview.getState().getId(),"12345");

    }



    @Test
    public void deleteReviewByIdTest(){
        ByProjectKeyReviewsRequestBuilder byProjectKeyReviewsRequestBuilder = Mockito.mock(ByProjectKeyReviewsRequestBuilder.class);
        Mockito.when(apiRoot.reviews()).thenReturn(byProjectKeyReviewsRequestBuilder);

        ByProjectKeyReviewsByIDRequestBuilder byProjectKeyReviewsByIDRequestBuilder = Mockito.mock(ByProjectKeyReviewsByIDRequestBuilder.class);
        Mockito.when(byProjectKeyReviewsRequestBuilder.withId("1234567890")).thenReturn(byProjectKeyReviewsByIDRequestBuilder);

        ByProjectKeyReviewsByIDDelete byProjectKeyReviewsByIDDelete = Mockito.mock(ByProjectKeyReviewsByIDDelete.class);
        Mockito.when(byProjectKeyReviewsByIDRequestBuilder.delete("1")).thenReturn(byProjectKeyReviewsByIDDelete);

        ApiHttpResponse<Review> reviewApiHttpResponse = Mockito.mock(ApiHttpResponse.class);
        Mockito.when(byProjectKeyReviewsByIDDelete.executeBlocking()).thenReturn(reviewApiHttpResponse);
        Mockito.when(reviewApiHttpResponse.getBody()).thenReturn(review );

        Review expectedReview = reviewService.deleteReviewById(review.getId(),"1");

//       .when(reviewService.deleteReviewById(review.getId(),"1")).thenReturn(ResponseEntity.ok("SUCCESS"));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/reviews/1234567890")
//                        //.param("version", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

        Assertions.assertEquals(expectedReview.getKey(),"test-review");
        Assertions.assertEquals(expectedReview.getText(),"Test-Text");
        Assertions.assertEquals(expectedReview.getTitle(),"Test-Title");
        Assertions.assertEquals(expectedReview.getRating(),3);
        Assertions.assertEquals(expectedReview.getCustomer().getId(),"1234567890");
        Assertions.assertEquals(expectedReview.getTarget().getId(),"12345");
        Assertions.assertEquals(expectedReview.getState().getId(),"12345");
    }

}
