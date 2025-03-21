package com.roniantonius.barang.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
	UserDetails authenticate(String email, String password);
	String generateToken(UserDetails userDetails); // method untku mengambil token dalam JWT
	UserDetails validateToken(String token); // validasi token JWT yang dibuat
}
