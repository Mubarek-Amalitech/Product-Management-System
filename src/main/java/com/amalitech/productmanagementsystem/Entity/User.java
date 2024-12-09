package com.amalitech.productmanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Principal {
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID ID;
     @Column(nullable = false,unique = true)
     private  String email;
     @Column(nullable = false)
     private  String password;
     private String username;
    private  boolean isEnabled;
     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return Collections.singleton(new SimpleGrantedAuthority("ANY"));
     }
     @Override
     public String getPassword() {
          return password;
     }
     @Override
     public String getUsername() {
          return email;
     }

     @Override
     public boolean isEnabled() {
          return isEnabled;
     }
     @Override
     public String getName() {
          return username;
     }

}
