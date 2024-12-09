package com.amalitech.productmanagementsystem.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
public record AuthenticationRequest(
        @NotEmpty(message = "email cannot be empty") @Email(message = "enter a valid email") String email,
        @NotEmpty(message = "password cannot be empty") String password) {
}
