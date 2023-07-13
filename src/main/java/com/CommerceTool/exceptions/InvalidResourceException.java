package com.CommerceTool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class InvalidResourceException extends RuntimeException{

    String resourceName;
    String fieldName;
    String fieldValue;


    public InvalidResourceException(String resourceName,String fieldName,String fieldValue) {
        //super("User not found with "+fieldName+fieldValue);
        super(String.format("%s with id %s has Invalid DiscountCode name %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}

