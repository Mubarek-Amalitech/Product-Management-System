package com.amalitech.productmanagementsystem.Controllers;

import com.amalitech.productmanagementsystem.Requests.AuthenticationRequest;
import com.amalitech.productmanagementsystem.Requests.RegisterRequest;
import com.amalitech.productmanagementsystem.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
private  final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> Register (@RequestBody RegisterRequest request){
         authenticationService.RegisterUser(request);
           return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public  ResponseEntity<?>  login(@RequestBody AuthenticationRequest authenticationRequest) {
        return  ResponseEntity.ok().body(authenticationService.login(authenticationRequest));

    }

}
