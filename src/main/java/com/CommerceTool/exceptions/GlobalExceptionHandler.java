package com.CommerceTool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidResourceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> InvalidResourceExceptionHandler(InvalidResourceException ex)
    {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NullPaymentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> NullPaymentExceptionHandler(NullPaymentException ex)
    {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<ApiResponse> InvalidActionExceptionHandler(InvalidActionException ex)
    {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(InvalidResourceIDException.class)
    public ResponseEntity<ApiResponse> InvalidResourceIDException(InvalidResourceIDException ex)
    {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }


}
