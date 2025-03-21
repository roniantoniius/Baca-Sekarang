package com.roniantonius.barang.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.roniantonius.barang.services.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final AuthenticationService authenticationService;
	
	@Override protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)throws ServletException, IOException {
	
		// TODO Auto-generated method stub
		
		try {
			String tokenString = extractToken(request);
			if (tokenString != null) {
				UserDetails userDetails = authenticationService.validateToken(tokenString);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				if (userDetails instanceof BarangUserDetails) {
					request.setAttribute("userId", ((BarangUserDetails) userDetails).getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.warn("Menerima token autentikasi tidak valid");
		}
		
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
