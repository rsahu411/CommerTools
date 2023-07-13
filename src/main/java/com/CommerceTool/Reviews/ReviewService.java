package com.CommerceTool.Reviews;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.ProductResourceIdentifier;
import com.commercetools.api.models.review.*;
import com.commercetools.api.models.state.StateResourceIdentifier;
import com.commercetools.api.models.type.CustomFieldsDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    ProjectApiRoot apiRoot;
    @Autowired
    ReviewRepository repository;


    // Create Review
    public Review createReview(ReviewDTO reviewDTO) {

        ReviewDraft reviewDraft = ReviewDraft
                .builder()
                .key(reviewDTO.getKey())
                .authorName(reviewDTO.getAuthorName())
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .rating(reviewDTO.getRating())
                .target(ProductResourceIdentifier.builder().id(reviewDTO.getProductId()).build())
//                .state(StateResourceIdentifier.builder()
//                        .key(reviewDTO.getStateKey())
//                        .build())
                .build();

        return repository.createReview(reviewDraft);
    }




    // Get All Reviews
    public ReviewPagedQueryResponse GetAllReview(String limit)
    {
        ReviewPagedQueryResponse reviews = apiRoot
                .reviews()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();

        return reviews;
    }



    // Get Review By Id
    public Review getReviewById(String id)
    {
        Review review = apiRoot
                .reviews()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();

        return review;
    }




    // Delete Review By Id
    public Review deleteReviewById(String id,String version)
    {
        Review review = apiRoot
                .reviews()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();

        return review;
    }



    // Set transitionState
    public Review setTransitionState(ReviewDTO reviewDTO, String id)
    {
//        Review review = apiRoot.reviews().withId(id).get().executeBlocking().getBody();

        Review review =getReviewById(id);

        ReviewUpdate reviewUpdate = ReviewUpdate
                .builder()
                .version(review.getVersion())
                .actions(ReviewUpdateAction
                        .transitionStateBuilder()
                        .state(StateResourceIdentifier
                                .builder()
                                .key(reviewDTO.getStateKey())
                                .build())
                        .build())
                .build();

        Review updatedReview = apiRoot
                .reviews()
                .withId(review.getId())
                .post(reviewUpdate)
                .executeBlocking()
                .getBody();
        return updatedReview;
    }
}
