package com.CommerceTool.exceptions;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;

}