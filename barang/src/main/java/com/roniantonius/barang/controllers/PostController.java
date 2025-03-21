package com.roniantonius.barang.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.dtos.PostDto;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.mappers.PostMapper;
import com.roniantonius.barang.services.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final PostMapper postMapper;
	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID tagId
			){
		List<Post> posts = postService.getAllPosts(categoryId, tagId);
		List<PostDto> postDtos = posts.stream().map(postMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(postDtos);
	}
}
