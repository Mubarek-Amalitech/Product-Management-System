package com.amalitech.productmanagementsystem.SecurityConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtKeyConfig  {
    private String jwtKey;
}
