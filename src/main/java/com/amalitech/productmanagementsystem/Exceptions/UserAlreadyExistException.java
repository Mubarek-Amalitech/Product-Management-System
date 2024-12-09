package com.amalitech.productmanagementsystem.Exceptions;

public class UserAlreadyExistException extends  RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
