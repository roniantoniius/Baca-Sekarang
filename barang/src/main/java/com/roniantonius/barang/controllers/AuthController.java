package com.roniantonius.barang.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.dtos.AuthResponse;
import com.roniantonius.barang.domain.dtos.LoginRequest;
import com.roniantonius.barang.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
	private final AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String token = authenticationService.generateToken(userDetails);
		AuthResponse authResponse = AuthResponse.builder()
				.token(token)
				.expiresIn(86400L)
				.build();
		return ResponseEntity.ok(authResponse);
	}
}
