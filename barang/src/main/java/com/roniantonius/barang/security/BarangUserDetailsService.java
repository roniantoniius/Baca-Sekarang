package com.roniantonius.barang.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.roniantonius.barang.domain.entities.User;
import com.roniantonius.barang.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarangUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User pengguna = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Pengguna tidak ditemukan dengan email " + email));
		return new BarangUserDetails(pengguna);
	}
	
}
