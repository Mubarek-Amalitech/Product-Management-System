package com.amalitech.productmanagementsystem.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {

    @Value("${key}")
    private  String key;

    public  String generateToken(Map<String,?> claims, UserDetails userDetails){

        return buildToken(claims,userDetails);

    }

    private String buildToken(Map<String,?> claims, UserDetails userDetails) {
        Date IssuedAt= new Date(System.currentTimeMillis());
        Date ExpiresIn= new Date(IssuedAt.getTime()+ 60 * 60 * 1000);

        return Jwts.builder().claims(claims).issuedAt(IssuedAt).expiration(ExpiresIn)
                .subject(userDetails.getUsername()).signWith(generateKey()).compact();
    }

    private SecretKey generateKey() {
        byte[] secretKey= Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(secretKey);
    }
    public String extractUsername(String token){
         return  extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims= extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return  Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    private  boolean IsNotExpired(String token){
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
         String username=  this.extractUsername(token);
         return username.equals(userDetails.getUsername()) && this.IsNotExpired(token);
    }
}
