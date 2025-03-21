package com.roniantonius.barang.services.impl;

import java.awt.RenderingHints.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.roniantonius.barang.services.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	
	@Value("${jwt.secret}") // kunci jwt untuk verifikasi token yang dibuat di aplikasi kita
	private String secretKey;
	private final Long jwtExpiryMs = 86400000L;
	
	@Override public UserDetails authenticate(String email, String password) {
		// TODO Auto-generated method stub
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); // verifikasi
		return userDetailsService.loadUserByUsername(email);
	}

	@Override public String generateToken(UserDetails userDetails) { // token untuk sesi pengguna akses
		// TODO Auto-generated method stub
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
			.signWith((java.security.Key) generateKunci(), SignatureAlgorithm.HS256)
			.compact();
			
	}
	
	private Key generateKunci() { // untuk verifikasi user tanpa simpan cache
		byte[] kunciBytes = secretKey.getBytes();
		return (Key) Keys.hmacShaKeyFor(kunciBytes);
	}

	@Override
	public UserDetails validateToken(String token) { // validasi token yang dibuat oleh Jwt sebelumnya apakah UserDetails ada di dalamnya
		// Untuk validasi token, kita harus ambil username (email) dari token ini
		// TODO Auto-generated method stub
		String username = extractUsername(token);
		return userDetailsService.loadUserByUsername(username);
	}
	
	private String extractUsername(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey((java.security.Key) generateKunci())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
}
