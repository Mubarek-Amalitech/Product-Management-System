package com.amalitech.productmanagementsystem.Exceptions;

public class ProductNotFoundException  extends  RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
