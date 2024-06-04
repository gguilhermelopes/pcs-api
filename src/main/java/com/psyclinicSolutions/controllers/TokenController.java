package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/token")
public class TokenController {
    @Autowired
    private TokenService service;

    @RequestMapping(value = "/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token){
        token = token.replace("Bearer ", "");
        try {
            Map<String, Object> claims = service.validateToken(token);
            return ResponseEntity.ok().body(Map.of("status", "valid", "claims", claims));
        } catch(JwtException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", "invalid",
                    "message", exception.getMessage()
            ));
        }
    }
}
