package com.teofilus.todoapp.Utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teofilus.todoapp.Config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    
    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(String username){
        Key key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret().getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret().getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
