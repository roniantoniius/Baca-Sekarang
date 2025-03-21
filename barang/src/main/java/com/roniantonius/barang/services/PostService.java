package com.roniantonius.barang.services;

import java.util.List;
import java.util.UUID;

import com.roniantonius.barang.domain.entities.Post;

public interface PostService {
	List<Post> getAllPosts(UUID categoryId, UUID tagId);
}
