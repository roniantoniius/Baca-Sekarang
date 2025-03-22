package com.roniantonius.barang.services;

import java.util.List;
import java.util.UUID;

import com.roniantonius.barang.domain.CreatePostRequest;
import com.roniantonius.barang.domain.UpdatePostRequest;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.domain.entities.User;

public interface PostService {
	List<Post> getDraftPosts(User user);
	List<Post> getAllPosts(UUID categoryId, UUID tagId);
	Post createPost(CreatePostRequest createPostRequest, User user);
	Post updatePost(UpdatePostRequest updatePostRequest, UUID id);
	Post getPost(UUID id);
	void deletePost(UUID id);
}
