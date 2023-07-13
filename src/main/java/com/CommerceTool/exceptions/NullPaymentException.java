package com.CommerceTool.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NullPaymentException extends RuntimeException{

    String resourceName;
    String fieldName;
    String fieldValue;


    public NullPaymentException(String resourceName,String fieldName) {
        //super("User not found with "+fieldName+fieldValue);
        super(String.format("%s with id %s has no payment",resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
