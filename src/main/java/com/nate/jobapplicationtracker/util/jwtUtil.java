package com.nate.jobapplicationtracker.util;

import com.nate.jobapplicationtracker.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class jwtUtil {

    private static final String SECRET_KEY = "my-secret-key-for-login-in-should-be-long";

    private static final long EXPiRATION_TIME = 1000 * 60 * 60 * 24;

    public static String generateToken(String username, Role role){
        return Jwts.builder()
                .setSubject(username)                //username as subject
                .claim("Role",role)                 //added roles as claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPiRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean tokenValidation(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            return true;

        }
        catch (Exception e){
            return false; // token invalid
        }
    }


    public static String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


    public static String extractRoles(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("Role",String.class);
    }
}
