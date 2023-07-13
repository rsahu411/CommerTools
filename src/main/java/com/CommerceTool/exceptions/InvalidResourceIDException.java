package com.CommerceTool.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvalidResourceIDException extends RuntimeException {

    String resourceName;
    String fieldValue;


    public InvalidResourceIDException(String resourceName, String fieldValue) {
        super(String.format("%s with id %s has not found",resourceName,fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
