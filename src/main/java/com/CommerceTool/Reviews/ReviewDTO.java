package com.CommerceTool.Reviews;

import lombok.Data;

@Data
public class ReviewDTO {

    private String key;
    private String authorName;
    private String title;
    private String text;
    private Integer rating;
    private String productId;
    private String stateId;
    private String stateKey;



}
