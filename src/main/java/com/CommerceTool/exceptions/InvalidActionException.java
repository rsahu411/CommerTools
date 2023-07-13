package com.CommerceTool.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class InvalidActionException extends RuntimeException {

    String resourceName;

    public InvalidActionException(String resourceName) {
        super(String.format("%s is Invalid Action",resourceName));
        this.resourceName = resourceName;
    }
}
