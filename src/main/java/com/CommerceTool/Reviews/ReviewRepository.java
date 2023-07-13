package com.CommerceTool.Reviews;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.review.Review;
import com.commercetools.api.models.review.ReviewDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Review createReview(ReviewDraft reviewDraft) {

        return apiRoot
                .reviews()
                .post(reviewDraft)
                .executeBlocking()
                .getBody();
    }
}
