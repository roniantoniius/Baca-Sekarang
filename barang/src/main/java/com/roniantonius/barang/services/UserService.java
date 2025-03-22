package com.roniantonius.barang.services;

import java.util.UUID;

import com.roniantonius.barang.domain.entities.User;

public interface UserService {
	User getUserById(UUID id);
}
