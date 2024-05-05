package com.example.auth.template.auth.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.auth.template.auth.models.AuthenticationRequest;
import com.example.auth.template.auth.models.AuthenticationResponse;
import com.example.auth.template.auth.services.auth.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @GetMapping("/validate-token")
    public ResponseEntity<?> validate(@RequestParam String token) {
        try {
            return ResponseEntity.ok(jwtService.validateJwtToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {

        Authentication authRequest = UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
        Authentication authResponse;
        try {
            authResponse = authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String jwt = jwtService.generateToken((UserDetails) authResponse.getPrincipal());

        return ResponseEntity.ok().body(new AuthenticationResponse(jwt));
    }
}
