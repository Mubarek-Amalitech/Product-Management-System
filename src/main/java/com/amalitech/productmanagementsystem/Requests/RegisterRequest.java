package com.amalitech.productmanagementsystem.Requests;

public record RegisterRequest(String username, String email,String password,String confirmPassword) {
}
