package com.psyclinicSolutions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {
    @Autowired
    private JwtDecoder jwtDecoder;

    public Map<String, Object> validateToken(String token) throws JwtException {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }
}
