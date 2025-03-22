package com.roniantonius.barang.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.barang.domain.entities.User;
import com.roniantonius.barang.repositories.UserRepository;
import com.roniantonius.barang.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;

	@Override
	public User getUserById(UUID id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User tidak ditemukan dengan id "+ id));
	}
}
