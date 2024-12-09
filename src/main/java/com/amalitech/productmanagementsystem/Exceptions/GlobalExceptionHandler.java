package com.amalitech.productmanagementsystem.Exceptions;
import com.amalitech.productmanagementsystem.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse>   ProductNotFoundException(ProductNotFoundException productNotFoundException) {
         return  new ResponseEntity< >( new ErrorResponse(productNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
}
